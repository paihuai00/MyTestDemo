package com.csx.mlibrary.skin;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatViewInflater;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.skin.attrs.SkinAttr;
import com.csx.mlibrary.skin.attrs.SkinView;
import com.csx.mlibrary.skin.support.SkinAppCompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.VectorEnabledTintResources.MAX_SDK_WHERE_REQUIRED;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/23
 * @description:
 */
public abstract class BaseSkinActivity extends BaseActivity {
    private static final String TAG = "BaseSkinActivity";
    private SkinAppCompatViewInflater mAppCompatViewInflater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(mLayoutInflater, new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //拦截到View创建，获取View 之后去解析
                //1,创建View
                View mView = createView(parent, name, context, attrs);
                Log.d(TAG, "onCreateView:   mView = " + mView);

                //2,解析属性 src background textColor 以及自定义属性
                //2.1 一个Activity 对应多个 SkinView
                if (mView != null) {
                    List<SkinAttr> skinAttrs = SkinSupport.getSkinAttrs(context, attrs);
                    SkinView skinView = new SkinView(mView, skinAttrs);

                    //3,统一管理(SkinManager)
                    managerSkinView(skinView);
                }

                return mView;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });

        super.onCreate(savedInstanceState);
    }

    /**
     * 统一管理，skinView
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViewList = SkinManager.getInstance().getSkinViews(this);
        if (skinViewList == null) {
            skinViewList = new ArrayList<>();

            SkinManager.getInstance().register(this, skinViewList);
        }

        skinViewList.add(skinView);
    }

    //下面的是拷贝的AppCompatDelegateImplV9，兼容
    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            TypedArray a = this.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme);
            String viewInflaterClassName =
                    a.getString(android.support.v7.appcompat.R.styleable.AppCompatTheme_viewInflaterClass);
            if ((viewInflaterClassName == null)
                    || AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                // Either default class name or set explicitly to null. In both cases
                // create the base inflater (no reflection)
                mAppCompatViewInflater = new SkinAppCompatViewInflater();
            } else {
                try {
                    Class viewInflaterClass = Class.forName(viewInflaterClassName);
                    mAppCompatViewInflater =
                            (SkinAppCompatViewInflater) viewInflaterClass.getDeclaredConstructor()
                                    .newInstance();
                } catch (Throwable t) {
                    Log.i(TAG, "Failed to instantiate custom view inflater "
                            + viewInflaterClassName + ". Falling back to default.", t);
                    mAppCompatViewInflater = new SkinAppCompatViewInflater();
                }
            }
        }

        boolean inheritContext = false;
        if ( Build.VERSION.SDK_INT < 21) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                Build.VERSION.SDK_INT < 21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private  boolean shouldBeUsed() {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled()
                && Build.VERSION.SDK_INT <= MAX_SDK_WHERE_REQUIRED;
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }
}
