package com.csx.mytestdemo.video_player;

import android.os.Bundle;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @Created by cuishuxiang
 * @date 2018/2/2.
 * <p>
 * 视频播放，参考：https://github.com/lipangit/JiaoZiVideoPlayer
 * <p>
 * 视频：http://sjg.pdoca.com/res/kaoshi/2018_5_3/636609591503022550.flv
 * mp3：http://sjg.pdoca.com/res/kaoshi/2018_5_3/636609545896006456.mp3
 */

public class VideoPlayerActivity extends BaseActivity {
    private static final String TAG = "VideoPlayerActivity";

    @BindView(R.id.jz_player)
    JZVideoPlayerStandard mJzPlayer;
    private String[] mp3Urls = new String[]{"http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"};


    private String mp3Url = "http://sjg.pdoca.com/res/kaoshi/2018_5_3/636609545896006456.mp3";
    private String flvUrl = "http://sjg.pdoca.com/res/kaoshi/2018_5_3/636609591503022550.flv";
    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        mJzPlayer.setUp(flvUrl
                , JZVideoPlayer.SCREEN_WINDOW_NORMAL, "嫂子闭眼睛");
//        mJzPlayer.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        if (mJzPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mJzPlayer != null)
            mJzPlayer.releaseAllVideos();
    }

}
