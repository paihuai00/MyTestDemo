package com.csx.mytestdemo.web_agentweb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.csx.mytestdemo.R;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

/**
 * Date: 2019/12/24
 * create by cuishuxiang
 * description:
 */
public class AgentWebUtils {

    public static AgentWeb getInstance(ViewGroup parentView,Object activityOrFragment,String url) {

        Activity activity = null;
        Fragment fragment = null;
        AgentWeb.AgentBuilder agentBuilder = null;
        if (activityOrFragment instanceof Activity) {
            activity = (Activity) activityOrFragment;
            agentBuilder = AgentWeb.with(activity);
        } else if (activityOrFragment instanceof Fragment) {
            fragment = (Fragment) activityOrFragment;
            agentBuilder = AgentWeb.with(fragment);
        }

        if (agentBuilder == null) {
            throw new IllegalArgumentException("AgentWebUtils Please enter Activity or Fragment!");
        }

        AgentWeb mAgentWeb = agentBuilder
                .setAgentWebParent(parentView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                //.setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(url);

        return mAgentWeb;
    }

    private static WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //这里接收到web标题
            System.out.println(" AgentWeb onReceivedTitle : " + title);
        }
    };

    private static WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //网页加载完成
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

    };

}
