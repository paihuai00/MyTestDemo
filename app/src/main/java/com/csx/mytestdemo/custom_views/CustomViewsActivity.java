package com.csx.mytestdemo.custom_views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

/**
 * Author: cuishuxiang
 * Date: 2020/6/19 15:26
 * Description: 常见自定义view
 */
public class CustomViewsActivity extends BaseActivity {
    private static final String TAG = "CustomViewsActivity";

    private TextView tv_content;
    private ImageView iv_load;

    ColorProgress cpv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_views;
    }

    @Override
    public void initView() {

        initTv();

        initIv();


        initProgress();
    }

    private void initProgress() {
        cpv = findViewById(R.id.cpv);
        cpv.setCurProgressPercentWithAnim(10);
//        cpv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cpv.setCurProgressPercentWithAnim(10);
//            }
//        });
    }


    private void initIv() {
        iv_load = findViewById(R.id.iv_load);
        AnimationDrawable d = (AnimationDrawable) iv_load.getBackground();
        d.start();
    }

    private void initTv() {
        tv_content = findViewById(R.id.tv_content);


        SpannableString spannableString = new SpannableString(" 上海市事实上111上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上上海市事实上212上海市事实上上海市事实上 ");
//        //得到drawable对象，即所要插入的图片
//
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_photo_nor);

        ImageSpan span = new MyImageSpan(this,bitmap);
        spannableString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_content.setText(spannableString);



    }

    @Override
    public void initData() {

    }
}
