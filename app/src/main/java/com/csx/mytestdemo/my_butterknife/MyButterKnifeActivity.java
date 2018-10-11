package com.csx.mytestdemo.my_butterknife;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.my_butterknife.ioc.CheckNet;
import com.csx.mytestdemo.my_butterknife.ioc.OnMyClick;
import com.csx.mytestdemo.my_butterknife.ioc.ViewById;
import com.csx.mytestdemo.my_butterknife.ioc.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/10/9
 * @description:
 */
public class MyButterKnifeActivity extends BaseActivity {

    @ViewById(R.id.btn_show)
    Button mBtnShow;
    @ViewById(R.id.iv_girl)
    ImageView mIvGirl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_butterknife;
    }

    @Override
    public void initView() {
        ViewUtils.inject(this);
    }

    @Override
    public void initData() {

        AsyncTask mAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //ui线程
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //UI线程
            }

            @Override
            protected Void doInBackground(Void... voids) {
                //子线程，处理耗时操作
                return null;
            }
        };
        mAsyncTask.execute();
    }

    @CheckNet     //网络检测
    @OnMyClick({R.id.btn_show, R.id.iv_girl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                showShortToast("点击了btn");
                break;
            case R.id.iv_girl:
                showShortToast("点击了图片");
                break;
        }
    }
}
