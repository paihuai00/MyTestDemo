package com.csx.mytestdemo.video_player;

import com.csx.mlibrary.BaseActivity;
import com.csx.mytestdemo.R;

/**
 * @Created by cuishuxiang
 * @date 2018/2/2.
 *
 * 视频播放，参考：https://github.com/lipangit/JiaoZiVideoPlayer
 */

public class VideoPlayerActivity extends BaseActivity {


    private String[] mp3Urls = new String[]{"http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
