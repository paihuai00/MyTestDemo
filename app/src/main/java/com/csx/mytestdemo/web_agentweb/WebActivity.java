package com.csx.mytestdemo.web_agentweb;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;
import com.just.agentweb.AgentWeb;

/**
 * Date: 2019/12/24
 * create by cuishuxiang
 * description: AgentWeb 使用
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.ll_root) LinearLayout mLlRoot;

    private AgentWeb mAgentWeb;
    private String url = "https://www.baidu.com";
    @Override
    public int getLayoutId() {
        return R.layout.activity_agentweb;
    }

    @Override
    public void initView() {
        mAgentWeb = AgentWebUtils.getInstance(mLlRoot, this, url);
    }

    @Override
    public void initData() {

    }

    //处理返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //生命周期管理
    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}

