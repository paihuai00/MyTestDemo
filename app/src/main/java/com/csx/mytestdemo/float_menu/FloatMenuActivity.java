package com.csx.mytestdemo.float_menu;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/3/29.
 * @description: 悬浮窗 按钮
 */

public class FloatMenuActivity extends BaseActivity {
    private static final String TAG = "FloatMenuActivity";
    
    @BindView(R.id.shutdown_system_btn)
    Button mShutdownSystemBtn;
    @BindView(R.id.fab_btn)
    FloatingActionButton mFabBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_float_menu;
    }

    @Override
    public void initView() {

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item_menu, null);
        ImageView menu_iv = view.findViewById(R.id.menu_iv);
        TextView menu_tv = view.findViewById(R.id.menu_tv);


        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_voice));


        FloatingActionMenu fabMenu=new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .addSubActionView(rLSubBuilder.setContentView(view).build())
                .attachTo(mFabBtn)
                .build();

        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("点击了——1");
            }
        });
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_voice));
        rlIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("点击了——2");
            }
        });
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_voice));
        rlIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("点击了——3");
            }
        });
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_voice));
        rlIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("点击了——4");
            }
        });

        fabMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                Log.d(TAG, "onMenuOpened: ");
//                mFabBtn.setRotation(45);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                Log.d(TAG, "onMenuClosed: ");
//                mFabBtn.setRotation(45);
            }
        });

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.shutdown_system_btn)
    public void onViewClicked() {
//       shutDowm();
    }

    /**
     * 关机
     */
//    private void shutDowm() {
//        Log.v(TAG, "shutDowm");
//        try {
//            //获得ServiceManager类
//            Class ServiceManager = Class
//                    .forName("android.os.ServiceManager");
//            //获得ServiceManager的getService方法
//            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
//            //调用getService获取RemoteService
//            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
//            //获得IPowerManager.Stub类
//            Class cStub = Class
//                    .forName("android.os.IPowerManager$Stub");
//            //获得asInterface方法
//            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
//            //调用asInterface方法获取IPowerManager对象
//            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
//            //获得shutdown()方法
//            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);
//            //调用shutdown()方法
//            shutdown.invoke(oIPowerManager, false, true);
//        } catch (Exception e) {
//            Log.e(TAG, e.toString(), e);
//        }
//
//    }

}
