package com.csx.mytestdemo.ksoap_webservice;

import android.os.Handler;
import android.os.Message;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Created by cuishuxiang
 * @date 2018/3/19.
 * @description: 调用WebService的简单封装
 */

public class WebServiceUtil {
    private static String jsonData;
    private final static int SOAP_VERSION = SoapEnvelope.VER11;
    private static boolean isSupportDotNet = true;//是否支持.net

    // 含有3个线程的线程池
    private static final ExecutorService executorService = Executors
            .newFixedThreadPool(3);

    public static void callWebService(String webServiceUrl, final String nameSpace, final String methodName,
                                      HashMap<String, String> properties,
                                      final WebServiceCallBack webServiceCallBack) {
        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE httpTransportSE = new HttpTransportSE(webServiceUrl);
        httpTransportSE.debug = true;
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(nameSpace, methodName);

        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet()
                    .iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }

        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
                SOAP_VERSION);

        soapEnvelope.dotNet = isSupportDotNet;//设置是否调用的是.Net开发的WebService
        soapEnvelope.bodyOut = soapObject;


        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
                String result = ((SoapObject) msg.obj).getProperty(0).toString();

                webServiceCallBack.callBack(result);
            }
        };

        // 开启线程去访问WebService
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                SoapObject resultSoapObject = null;
                try {
                    httpTransportSE.call(nameSpace + methodName, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
                        //jsonData=soapEnvelope.getResponse().toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    // 将获取的消息利用Handler发送到主线程
                    mHandler.sendMessage(mHandler.obtainMessage(0,
                            resultSoapObject));
                }
            }
        });
    }

    public interface WebServiceCallBack {
//        public void callBack(SoapObject result);
        public void callBack(String result);
    }

}