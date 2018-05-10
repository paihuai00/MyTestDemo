package com.csx.mytestdemo.webview_progressbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.widget.ProgressWebView;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/5/2.
 * @description: webView + progressBar 封装
 */

public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";
    @BindView(R.id.progress_wv)
    ProgressWebView mProgressWv;
    @BindView(R.id.back_wb_btn)
    Button mBackWbBtn;

    private ProgressBar myGressBar;
    private WebView myWebView;

    private String web_url = "http://sjg.pdoca.com/res/kaoshi/2018_5_3/636609545896006456.mp3";

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        initWebView();
    }

    @Override
    public void initData() {

    }

    /**
     * 初始化封装的 progressbar  + webview
     */
    private void initWebView() {
        myGressBar = mProgressWv.getProgressbar();
        myWebView = mProgressWv.getWebView();

        // 开启 localStorage
        myWebView.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        myWebView.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        myWebView.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //使用自定义的WebViewClient
        myWebView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法,app内部打开h5
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        myWebView.loadUrl(web_url);

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG, "网页加载进度：" + newProgress);

                if (null == myGressBar) {
                    Log.d(TAG, "mHorizontalProgressbar = null");
                    return;
                }
                if (100 == newProgress) {
                    myGressBar.setVisibility(View.GONE);
//                    LoadingDialog.cancelDialogForLoading();
                } else {
                    if (View.GONE == myGressBar.getVisibility()) {
                        myGressBar.setVisibility(View.VISIBLE);
                    }
                    myGressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    public void destroyWebView() {
        if (myWebView != null) {
            myWebView.clearHistory();
            myWebView.clearCache(true);
            myWebView.loadUrl("about:blank");
            myWebView.freeMemory();
            myWebView.onPause();
            myWebView = null;
        }
    }


    @OnClick(R.id.back_wb_btn)
    public void onViewClicked() {
        finish();
    }
}
