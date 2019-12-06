package com.csx.mytestdemo.notification_test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

/**
 * Date: 2019/12/5
 * create by cuishuxiang
 * description: 通知
 */
public class NotificationActivity extends BaseActivity {

    @BindView(R.id.btn_simple_notify) Button mBtnSimpleNotify;
    @BindView(R.id.btn_bigtext_style) Button mBtnBigtextStyle;
    @BindView(R.id.btn_inbox_style) Button mBtnInboxStyle;
    @BindView(R.id.btn_pic_style) Button mBtnPicStyle;
    @BindView(R.id.btn_hengfu) Button mBtnHengFu;

    private NotificationManager notificationManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notify;
    }

    @Override
    public void initView() {
        //创建
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        //System.out.println("NotificationActivity 接收到 Name = " + name);
    }


    private void showSimpleNotification(NotificationCompat.Style style,boolean isShowHengfu) {
        NotificationCompat.Builder builder = null;
        /**
         * 这里需要注意，8.0以上需要创建 Channel 渠道
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("测试渠道", getString(R.string.app_name),
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, "测试渠道");
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        //Ticker是状态栏显示的提示
        builder.setTicker("显示setTicker");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("显示setContentTitle");
        //第二行内容 通常是通知正文
        builder.setContentText("这里显示setContentText");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("这里显示setSubText！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        builder.setContentInfo("这里显示ContentInfo");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //true：点击通知栏，通知消失
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //通知时间
        builder.setWhen(System.currentTimeMillis());
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        //设置通知样式
        if (style!=null) builder.setStyle(style);
        /**
         * 这里的Intent可以携带参数传递到跳转的Activity，后面会专门解释
         */
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("Name", "张三");
        //注意，这里不设置flag，无法接收到参数
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        //横幅通知
        if (isShowHengfu) builder.setFullScreenIntent(pIntent, true);

        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        /**
         * 第一个参数为id，如果id相同则该通知会更新；
         */
        notificationManager.notify((int)Math.random()*1000, notification);
    }

    private void showHengFuNotification() {
        NotificationCompat.Builder builder = null;
        /**
         * 这里需要注意，8.0以上需要创建 Channel 渠道
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("测试渠道", getString(R.string.app_name),
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, "测试渠道");
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        //Ticker是状态栏显示的提示
        builder.setTicker("显示setTicker");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("显示setContentTitle");
        //第二行内容 通常是通知正文
        builder.setContentText("这里显示setContentText");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("这里显示setSubText！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        builder.setContentInfo("这里显示ContentInfo");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2);
        //true：点击通知栏，通知消失
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //通知时间
        builder.setWhen(System.currentTimeMillis());
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        /**
         * 这里的Intent可以携带参数传递到跳转的Activity，后面会专门解释
         */
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        //横幅通知
        //builder.setFullScreenIntent(pIntent, true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        //点击跳转的intent
        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        /**
         * 第一个参数为id，如果id相同则该通知会更新；
         */
        notificationManager.notify((int)Math.random()*1000, notification);
    }

    @OnClick({
            R.id.btn_simple_notify, R.id.btn_bigtext_style, R.id.btn_inbox_style, R.id.btn_pic_style,R.id.btn_hengfu
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_notify:
                showSimpleNotification(null,false);
                break;
            case R.id.btn_bigtext_style:
                //可展开通知
                NotificationCompat.Style style = new NotificationCompat.BigTextStyle().bigText(
                        "Much longer text that cannot fit one line...Much longer text that cannot fit one line...Much longer text that cannot fit one line...Much longer text that cannot fit one line...Much longer text that cannot fit one line...");
                showSimpleNotification(style,false);
                break;
            case R.id.btn_inbox_style:
                NotificationCompat.Style inboxStyle =
                        new NotificationCompat.InboxStyle().setBigContentTitle("InboxStyle : ContentTitle")
                                .setSummaryText("InboxStyle; setSummaryText")
                                .addLine("This 1")
                                .addLine("This 2")
                                .addLine("This 3")
                                .addLine("This 4");
                showSimpleNotification(inboxStyle,false);
                break;
            case R.id.btn_pic_style:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.btn_photo_nor);
                Bitmap bigPictureBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_error);
                NotificationCompat.Style picStyle=new NotificationCompat.BigPictureStyle()
                        .setBigContentTitle("BigPictureStyle :setBigContentTitle")
                        .setSummaryText("BigPictureStyle : setSummaryText")
                        .bigPicture(bigPictureBitmap)//这张图片会显示在通知中间
                        .bigLargeIcon(bitmap);
                showSimpleNotification(picStyle,false);
                break;
            case R.id.btn_hengfu://显示横幅通知:
                showHengFuNotification();
                break;
        }
    }
}
