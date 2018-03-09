package com.csx.mytestdemo.share_mob;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mlibrary.utils.ToastUtils;
import com.csx.mytestdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * @Created by cuishuxiang
 * @date 2018/3/9.
 * @description:  封装分享工具类
 *
 * 可以直接链式添加数据，最后调用share()
 */

public class ShareUtils implements View.OnClickListener {
    private static final String TAG = "ShareUtils";
    private CommonDialog shareDialog;
    private Context mContext;


    //分享相关
    private String mShareTitle; //指定分享内容标题
    private String mShareContentText; //指定分享内容文本
    private String mShareImageUrl; //指定分享图片链接
    private String mUrl;// 分享的链接
    private int mShareType = Platform.SHARE_WEBPAGE;// 分享的类型
    private String mImagePath;
    private PlatformActionListener mListener;

    public ShareUtils(Context context) {
        mContext = context;
        init();
    }

    //初始化选择弹框
    private void init() {

        //创建弹框
        if (shareDialog == null) {
            shareDialog = new CommonDialog.Builder(mContext)
                    .setContentView(R.layout.dialog_share)
                    .setCancelable(false)
                    .setOnClickListener(R.id.tv_weibo,this)
                    .setOnClickListener(R.id.tv_wechat,this)
                    .setOnClickListener(R.id.tv_wechat_circle,this)
                    .setOnClickListener(R.id.tv_qq,this)
                    .setOnClickListener(R.id.tv_cancel,this)
                    .showFromBottom(true)
                    .create();
        }

        mListener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //成功回调
                Log.d(TAG, "onComplete: ");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                //错误回调
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                //取消回调
                Log.d(TAG, "onCancel: ");
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wechat_circle:
                ToastUtils.showShortToast("朋友圈");
                share(PlatformType.WeChatCircle);
                break;
            case R.id.tv_wechat:
                ToastUtils.showShortToast("微信");
                share(PlatformType.WeChat);
                break;
            case R.id.tv_qq:
                ToastUtils.showShortToast("QQ");
                share(PlatformType.QQ);
                break;
            case R.id.tv_weibo:
                ToastUtils.showShortToast("微博");
                share(PlatformType.WeiBo);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 分享
     *
     * @see PlatformType  平台
     * @param platformType
     */
    private void share(PlatformType platformType) {
        ShareData mData = new ShareData();
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(mShareType);
        params.setTitle(mShareTitle);
        params.setText(mShareContentText);
        params.setUrl(mUrl);
        params.setTitleUrl(mUrl);
        // 本地图片
        if (mImagePath != null) {
            params.setImagePath(mImagePath);
        }
        // 网络图片
        if (!TextUtils.isEmpty(mShareImageUrl)) {
            params.setImageUrl(mShareImageUrl);
        }
        mData.mPlatformType = platformType;
        mData.mShareParams = params;
        ShareManager.getInstance(mContext).shareData(mData, mListener);
        dismiss();
    }

    /**
     * 设置分享类型
     * @param shareType
     * @return
     */
    public ShareUtils shareType(int shareType) {
        mShareType = shareType;
        return this;
    }

    /**
     *
     * @param url
     * @return
     */
    public ShareUtils shareUrl(String url) {
        mUrl = url;
        return this;
    }

    public ShareUtils shareTitle(String shareTitle) {
        mShareTitle = shareTitle;
        return this;
    }

    /**
     * 分享内容
     * @param shareText
     * @return
     */
    public ShareUtils shareContentText(String shareText) {
        mShareContentText = shareText;
        return this;
    }

    /**
     *
     * @param shareImageUrl
     * @return
     */
    public ShareUtils shareImageUrl(String shareImageUrl) {
        mShareImageUrl = shareImageUrl;
        return this;
    }

    public ShareUtils shareImageRes(int imageRes) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageRes);
        // 缓存到本地
        File file = saveImage(bitmap);
        if (file != null) {
            mImagePath = file.getAbsolutePath();
        }
        return this;
    }


    /**
     * 缓存本地图片
     */
    public File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "shares.jpg";
        File file = new File(appDir, fileName);
        if (file.exists() && file.isFile()) {
            return file;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void show() {
        if (shareDialog!=null)
            shareDialog.show();
    }

    public void dismiss() {
        if (shareDialog!=null)
            shareDialog.dismiss();
    }


}
