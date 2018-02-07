package com.csx.mytestdemo.audio_record;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import com.csx.mlibrary.app.PathConstant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @Created by cuishuxiang
 * @date 2018/2/7.
 * <p>
 * MediaRecorder 录制音频工具类；
 */

public class AudioUtils {
    private static final String TAG = "AudioUtils";
    private Context mContext;
    private MediaRecorder mMediaRecorder;

    private boolean isRecording;

    //生成的录音文件
    private File audioFile;
    //文件名：为了避免重复，使用时间戳 (后缀，可以根据格式 改变为 mp4  wav等)
    private String fileName = System.currentTimeMillis() + "_audio.arm";
    //文件目录 ：默认为私有目录
    private String fileDir;

    public AudioUtils(Context context) {
        mContext = context;
    }

    public boolean isRecording() {
        return isRecording;
    }

    /**
     * 准备录音
     * boolean 为true 说明prepare没有问题
     */
    private boolean prepareRecord() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();

            //音频源头：麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //输出文件格式MPEG_4 对应生成文件后缀为：.m4a
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//可以设置为MPEG_4
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mMediaRecorder.setAudioChannels(1);//单声道，立体声
            mMediaRecorder.setAudioSamplingRate(44100);//采样率，44100为当前设备都支持的；
        }

        //得到目录
        fileDir = PathConstant.getMusicFilePath(mContext);

        audioFile = new File(fileDir, fileName);

        Log.d(TAG, "audioFile.getAbsolutePath为: " + audioFile.getAbsolutePath());

        mMediaRecorder.setOutputFile(audioFile.getPath());

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.d(TAG, "mMediaRecorder.prepare() : IOException");
            releaseMediaRecorder();
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * 开始录音
     */
    public void startAudioRecord() {
        //首先判断是否 prepare
        if (prepareRecord()) {
            try {
                mMediaRecorder.start();
                isRecording = true;
            } catch (Exception e) {
                Log.d(TAG, "startAudioRecord: Exception");
                releaseMediaRecorder();
            }
        }
    }

    /**
     * 停止录音 保存文件
     */
    public void stopAudioRecord() {
        if (isRecording) {
            isRecording = false;

            try {
                mMediaRecorder.stop();
            } catch (Exception e) {

                releaseMediaRecorder();
            }

            releaseMediaRecorder();

        }
    }

    /**
     * 停止录音，但是不保存文件
     */
    public void stopAudioRecordUnSave() {
        if (isRecording) {
            isRecording = false;
            try {
                mMediaRecorder.stop();

            } catch (RuntimeException e) {
                releaseMediaRecorder();
                if (audioFile.exists()) {
                    audioFile.delete();
                }
            }

            releaseMediaRecorder();

            if (audioFile.exists()) {
                audioFile.delete();
            }
        }

    }

    /**
     * 释放资源
     */
    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            Log.d(TAG, "release Recorder");
        }
    }

    /**
     * 获取生成文件的目录地址
     */
    public File  getAudioFile() {
        if (audioFile==null)
            return null;
        Log.d(TAG, "生成文件地址为: "+audioFile.getAbsolutePath());
        return audioFile;
    }

}
