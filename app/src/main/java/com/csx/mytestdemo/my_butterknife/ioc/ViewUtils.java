package com.csx.mytestdemo.my_butterknife.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/9
 * @description:  仿 butterknife
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 事件注入
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        Class<?> mClass = object.getClass();

        //1，获取所有的方法
        Method[] mMethods = mClass.getMethods();
        for (Method method : mMethods) {
            OnMyClick mOnMyClick = method.getAnnotation(OnMyClick.class);
            method.setAccessible(true);

            if (mOnMyClick != null) {
                //2，获取所有的value
                int[] viewIds = mOnMyClick.value();
                for (int viewId : viewIds) {
                    //3，找到view
                    View mView = finder.findViewById(viewId);

                    //扩展，检测网络
                    CheckNet mCheckNet = method.getAnnotation(CheckNet.class);
                    boolean isCheckNet = mCheckNet != null ? true : false;

                    if (mView != null) {
                        //4，设置点击事件
                        mView.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                    }

                }
            }
        }
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1，获取类中所有属性
        Class<?> mClass = object.getClass();

        //获取所有的属性（包括私有和共有）
        Field[] mFields = mClass.getDeclaredFields();

        //2, 获取ViewById的value值
        for (Field field : mFields) {
            ViewById mViewById = field.getAnnotation(ViewById.class);
            if (mViewById != null) {
                //获取到注解里面的id值
                int viewId = mViewById.value();

                //3， findViewById 找到 View
                View view = finder.findViewById(viewId);
                field.setAccessible(true);//注入所有的修饰符

                //4，动态的注入找到的View
                try {
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;
        private boolean isCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.isCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //首先判断是否需要判断网络
            if (isCheckNet) {
                if (!netAvailable(v.getContext())) {
                    Toast.makeText(v.getContext(), "网络不给力，请检测网络！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            //5，反射，执行该方法
            try {
                mMethod.invoke(mObject, v);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    //检测是否有网
    private static boolean netAvailable(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

            if (mNetworkInfo != null && mNetworkInfo.isConnected())
                return true;

        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
