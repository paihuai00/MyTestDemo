package com.csx.mytestdemo.service_test;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.csx.mlibrary.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Created by cuishuxiang
 * @date 2018/4/26.
 * @description: 1，serviceHandler：为了在服务中提示相应的msg
 * msg.what = 9 ： 连接失败
 */

public class SocketService extends Service {
    private static final String TAG = "SocketService";

    private ExecutorService mExecutorService;//开启线程池

    private Socket mSocket;
    private int timeOut = 5000;//超时时间
    private boolean isConnectSucceed = false;//是否连接成功

    //心跳包，使用一个定时器
    private Timer mTimer;
    private TimerTask mTimerTask;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    //标示位：是否结束
    private boolean isRunning = true;

    //标示位：是否重连
    private boolean isReConnected = true;

    //持有绑定activity的弱引用
    private WeakReference<Activity> mActivityWeakReference;


    //用于处理线程发送的消息，在主线程提示
//    private Handler serviceHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 9:
//                    String msgObj = (String) msg.obj;
//                    ToastUtils.showShortToast("连接失败，正在尝试重连.....");
//                    break;
//                case 10:
//                    ToastUtils.showShortToast("连接服务器成功！");
//                    break;
//            }
//
//        }
//    };
//
//    //接收数据的handler
//    private Handler mInHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                switch (msg.what) {
//                    case 0:
//                        if (msg.obj != null) {
//                            byte[] messBytes = null;
//                            messBytes = (byte[]) msg.obj;
//                            //校验头部
//                            byte[] head = new byte[4];
//                            System.arraycopy(messBytes, 0, head, 0, 4);
//                            if (CommonUtils.checkHead(head)) {
//                                elementListPre.clear();
//                                datelength = messBytes.length;
//                                elementListPre.add(messBytes);
//                                //获取数据的长度
//                                byte[] dataLength = new byte[4];
//                                System.arraycopy(messBytes, 8, dataLength, 0, 4);
//                                length = CommonUtils.bytes2int(dataLength);
//                                //获取命令
//                                System.arraycopy(messBytes, 4, er, 0, 4);
//                                if (messBytes.length == length + 12) {
//
//                                    byte[] allData = CommonUtils.byteMergerAll(elementListPre);
//                                    byte[] excludeHeadData = new byte[allData.length - 12];
//                                    System.arraycopy(allData, 12, excludeHeadData, 0, allData.length - 12);
//                                    String value1 = new String(excludeHeadData, "UTF-8");
//                                    setIntent(value1);
//                                }
//                            } else {
//                                datelength = messBytes.length + datelength;
//                                elementListPre.add(messBytes);
//                                if (datelength == length + 12) {
//                                    byte[] allData = CommonUtils.byteMergerAll(elementListPre);
//                                    byte[] excludeHeadData = new byte[allData.length - 12];
//                                    System.arraycopy(allData, 12, excludeHeadData, 0, allData.length - 12);
//                                    String value1 = new String(excludeHeadData, "UTF-8");
//                                    setIntent(value1);
//                                }
//
//                            }
//                        }
//
//                        break;
//                    case 9:
//                        Log.d("123", "case 9:----------------------------");
//                        closeSocket();
//                        break;
//                    case 10://连接成功发送用户数据
//                        if (isOk == 1) {
//                            sendMassage(200004);
//                        } else {
//                            sendMessage(200001);
//                        }
//
//                        break;
//                }
//
//            } catch (Exception ee) {
//                ee.printStackTrace();
//            }

//        }
//    };

    //向服务器发送消息的handler
    private Handler mOutHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://连接成功发送用户数据
                    Log.d("123", "发送成功");
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (isRun) {
//                                socketThread.sendXinTiao();
//                                try {
//                                    Thread.sleep(20000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//
//                        }
//                    }).start();


                    break;
                case 0://连接成功发送用户数据
//                    closeSocket();
//                    Log.d("123", "发送失败");

                    break;
                case 2://连接成功发送用户数据
                    Log.d("123", "心跳发送成功");
                    break;
                case 9://连接成功发送用户数据
                    Log.d(TAG, "handleMessage: case 9 ");
//                    closeSocket();
                    break;

            }

        }
    };

    private String ip;//服务器ip
    private int port;//端口

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        //初始化 socket
        initSocket();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");


        //ip = intent.getStringExtra("ip");
        // port = Integer.valueOf(intent.getStringExtra("port"));

        Log.d(TAG, "onStartCommand: 传入的Ip = " + ip + "  端口：" + port);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    /**
     * 初始化socket
     */
    private void initSocket() {
        if (mExecutorService == null)
            mExecutorService = Executors.newCachedThreadPool();

        //开启线程初始化socket
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //这里需要对 ip port 进行判断

                    mSocket = new Socket("10.0.2.3", 8080);
                    mSocket.setSoTimeout(timeOut);

                    isConnectSucceed = mSocket.isConnected();
                    Log.d(TAG, "initSocket: 连接服务器是否成功？" + isConnectSucceed);
                    if (isConnectSucceed) {//连接成功
                        sendMsgToMainHandler(10);

                        mOutputStream = mSocket.getOutputStream();
                        mInputStream = mSocket.getInputStream();

                        //发送心跳包
                        sendHeartData();

                        //接收数据
                        receivedData();


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "initSocket run: " + e.getMessage());
                    if (e instanceof SocketTimeoutException) {
                        Log.d(TAG, "run: 连接超时，正在重连");

                        releaseSocket();

                    } else if (e instanceof NoRouteToHostException) {
                        Log.d(TAG, "run: 该地址不存在，请检查");
                        stopSelf();

                    } else if (e instanceof ConnectException) {
                        Log.d(TAG, "run: 连接异常或被拒绝，请检查");
                        stopSelf();
                    }
                    //连接失败
                    sendMsgToMainHandler(9);
                }
            }
        });

        ToastUtils.showShortToast("连接成功！");
    }

    /**
     * 实时接收数据
     * 根据标示位，true : 一直循环接收
     */
    private void receivedData() {
//        mExecutorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                //定义每次接收数据大小 8k
//                byte[] msgBytes = new byte[8 * 1024];
//
//                while (isRunning) {
//                    try {
//                        int len = mInputStream.read(msgBytes);
//                        if (len > 0) {
//                            byte[] objByts = new byte[len];
//                            System.arraycopy(msgBytes, 0, objByts, 0, len);
//                            Message msg = mInHandler.obtainMessage();
//                            msg.obj = objByts;
//                            msg.what = 0;
//                            mInHandler.sendMessage(msg);// 结果返回给UI处理
//                        } else {
//                            break;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Log.d(TAG, "receivedData  - run: " + e.getMessage());
//                    }
//                }
//            }
//        });
    }

    /**
     * 发送数据
     *
     * @param bytes
     */
    private void sendBytes(final byte[] bytes) {
        //先判断是否连接成功
        if (mSocket == null && !isConnectSucceed) {
            releaseSocket();
        }

        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mOutputStream.write(bytes);
                    mOutputStream.flush();

                    Message msg = mOutHandler.obtainMessage();
                    msg.obj = bytes;
                    msg.what = 1;
                    mOutHandler.sendMessage(msg);// 结果返回给UI处理
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = mOutHandler.obtainMessage();
                    msg.what = 9;
                    mOutHandler.sendMessage(msg);// 结果返回给UI处理
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        //不再重新连接
        isReConnected = false;
        isRunning = false;
        releaseSocket();
    }

    /**
     * 释放资源
     */
    private void releaseSocket() {

        try {
            //释放定时器
            if (mTimerTask != null) {
                mTimerTask.cancel();
                mTimerTask = null;
            }
            if (mTimer != null) {
                mTimer.purge();
                mTimer.cancel();
                mTimer = null;
            }

            //释放输出流
            if (mOutputStream != null) {
                mOutputStream.close();
            }
            if (mInputStream != null)
                mInputStream.close();

            //释放socket
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }

            //关闭线程池
            if (mExecutorService != null) {
                mExecutorService.shutdownNow();
                mExecutorService = null;
            }

            //默认重连
            if (isReConnected) {
                initSocket();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MySocketBinder extends Binder {

        /**
         * 持有Activity的弱引用
         *
         * @param activityWeakReference
         */
        public void setActivity(WeakReference<Activity> activityWeakReference) {
            mActivityWeakReference = activityWeakReference;
        }
    }

    /**
     * 工具方法，需要在主线程提示的msg
     */
    private void sendMsgToMainHandler(int msgWhat) {
//        if (serviceHandler != null) {
//            Message message = serviceHandler.obtainMessage();
//            message.what = msgWhat;
//            serviceHandler.sendMessage(message);
//        } else {
//            Log.d(TAG, "showToastOnMain: serviceHandler == null");
//        }
    }

    /**
     * 发送心跳包
     */
    private void sendHeartData() {
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        mOutputStream = mSocket.getOutputStream();

                        //这里发送的数据，需要与服务器定义的一致
                        mOutputStream.write(("heart").getBytes("utf-8"));
                        mOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "run: 心跳包发送失败，需要重连");
                        /*发送失败说明socket断开了或者出现了其他错误*/
                        /*重连*/
                        releaseSocket();
                    }
                }
            };
        }

        //每2s发送一次
        mTimer.schedule(mTimerTask, 0, 2000);
    }
}
