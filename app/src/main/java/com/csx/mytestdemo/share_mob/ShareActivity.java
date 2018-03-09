package com.csx.mytestdemo.share_mob;

import android.widget.Button;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mlibrary.dialog.CommonDialog;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Created by cuishuxiang
 * @date 2018/3/9.
 * @description: mob 分享demo
 * <p>
 * appkey:1e75b99339080
 * appSecret：be22239b187c023eebaf766d3ac1ed31
 */

public class ShareActivity extends BaseActivity {
    @BindView(R.id.open_share_btn)
    Button mOpenShareBtn;

    CommonDialog shareDialog;

    private ShareUtils mShareUtils;
    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.open_share_btn)
    public void onViewClicked() {
//        if(shareDialog!=null)
//            shareDialog.show();

        mShareUtils = new ShareUtils(this);
        mShareUtils.shareTitle("分享3.9")
                .shareContentText("Content")
                .shareImageUrl("https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=6a92f71e24a446237ecaa264a0191533/3ac79f3df8dcd100bd117545768b4710b8122fc0.jpg")
                .shareUrl("https://www.baidu.com/")
                .show();

    }

}
