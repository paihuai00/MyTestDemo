package com.csx.mytestdemo.disklrucache_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.csx.mytestdemo.disklrucache_test.disk_utils.CompressHelper;
import com.csx.mytestdemo.disklrucache_test.disk_utils.DiskLruCacheHelper;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2019/6/25
 * create by cuishuxiang
 * description:  https://github.com/JakeWharton/DiskLruCache
 * 磁盘缓存
 */
public class DiskLruCacheActivity extends BaseActivity {
    private static final String TAG = "DiskLruCacheActivity";
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_clean_buffer)
    Button mBtnCleanBufer;
    @BindView(R.id.iv_src)
    ImageView mIvSrc;

    private DiskLruCacheHelper diskLruCacheHelper;
    private CompressHelper compressHelper;
    private MyAsyncTask downAsyncTask;
    private String url = "http://img.hb.aicdn.com/eca438704a81dd1fa83347cb8ec1a49ec16d2802c846-laesx2_fw658";


    @Override
    public int getLayoutId() {
        return R.layout.activity_disk_lru_cache;
    }

    @Override
    public void initView() {
        String cachePath = getCacheDir().getPath();//
        Log.d(TAG, "initView: getCacheDir().getPath() = " + cachePath);
        String fileDirPath = getFilesDir().getPath();//
        Log.d(TAG, "initView: getFilesDir().getPath() = " + fileDirPath);

        compressHelper = CompressHelper.getInstance();
        diskLruCacheHelper = new DiskLruCacheHelper(this);
        String key = KeyUtils.hashKeyFormUrl(url);
        downAsyncTask = new MyAsyncTask(diskLruCacheHelper.getEdit(key));

    }

    @Override
    public void initData() {

    }


    private Bitmap getBitmapFromDisk() {
        Bitmap bitmap = null;
        String key = KeyUtils.hashKeyFormUrl(url);
        try {
            //首先从缓存取
            DiskLruCache.Snapshot snapShot = diskLruCacheHelper.getDiskLruCache().get(key);
            if (snapShot != null) {
                FileInputStream fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                FileDescriptor fileDescriptor = fileInputStream.getFD();

                bitmap = compressHelper.compressDescriptor(fileDescriptor, 150, 150);
                if (bitmap != null) {
                    mTvDescription.setText(mTvDescription.getText().toString() + "\n" + "缓存加载" + "\n");
                    mIvSrc.setImageBitmap(bitmap);
                }
            } else {
                //取不到的话，走网络
                downAsyncTask.execute(url);//
            }


        } catch (Exception e) {

            return null;
        }

        return null;
    }

    @OnClick({R.id.btn_start, R.id.btn_clean_buffer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                //1,先从磁盘缓存
                getBitmapFromDisk();
                break;
            case R.id.btn_clean_buffer:
                //清空缓存
                diskLruCacheHelper.cleanBuffer();
                break;
        }
    }


    class MyAsyncTask extends AsyncTask<String, Integer, Boolean> {
        private static final String TAG = "DownAsyncTask";
        private DiskLruCache.Editor editor;
        private static final int BUFFER_SIZE = 8 * 1024;


        public MyAsyncTask(DiskLruCache.Editor editor) {
            super();
            this.editor = editor;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: " + Thread.currentThread().getName());
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Bitmap bitmap = null;

            HttpURLConnection httpURLConnection = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            try {
                //子线程
                String imageUrl = strings[0];
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);

                    URL url = new URL(imageUrl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream(), BUFFER_SIZE);
                    bufferedOutputStream = new BufferedOutputStream(outputStream, BUFFER_SIZE);

                    int length = 0;
                    while ((length = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(length);
                    }

                    editor.commit();//提交到磁盘缓存

                    diskLruCacheHelper.getDiskLruCache().flush();

                    bufferedInputStream.reset();


                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();

                //如果异常，终止editor
                try {
                    if (editor != null) {
                        editor.abort();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                return false;
            } finally {
                if (httpURLConnection != null) httpURLConnection.disconnect();
                try {
                    if (bufferedInputStream != null) bufferedInputStream.close();
                    if (bufferedOutputStream != null) bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate: " + Thread.currentThread().getName());
            Log.d(TAG, "onProgressUpdate: " + values);
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            if (b) {
                mTvDescription.setText(mTvDescription.getText().toString() + "\n" + "网络加载" + "\n");
                getBitmapFromDisk();
            }
        }
    }
}
