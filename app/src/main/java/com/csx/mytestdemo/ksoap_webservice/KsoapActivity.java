package com.csx.mytestdemo.ksoap_webservice;

import android.util.Log;

import com.csx.mlibrary.base.BaseActivity;
import com.csx.mytestdemo.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Created by cuishuxiang
 * @date 2018/3/15.
 * @description: android 与 webService 调用 ksoap框架的使用
 */

public class KsoapActivity extends BaseActivity {
    private static final String TAG = "KsoapActivity";


    //命名空间
//    private String nameSpace = "http://tempuri.org/";
    private String nameSpace = "http://WebXml.com.cn/";
    //方法名
//    private String methodName = "CheckUserJSon";
    private String methodName = "getSupportCity";

    private String ACTION = nameSpace + methodName;

    //    private String ServiceURL="http://w21.pdoca.com/webservice/wisdomclassws.asmx";
    private String ServiceURL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";

    @Override
    public int getLayoutId() {
        return R.layout.activity_ksoap;
    }

    @Override
    public void initView() {
        Log.d(TAG, "onResponse: " + Thread.currentThread().getName());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //第一步，指定WebService的命名空间和调用的方法名
//                    SoapObject soapObject = new SoapObject(nameSpace, methodName);
//                    //第二步， 设置需调用WebService接口需要传入的参数
////                    soapObject.addProperty("userLoginName", "jyzr");
////                    soapObject.addProperty("userPassword", "888888");
////                    soapObject.addProperty("userType", "学生");
//                    soapObject.addProperty("byProvinceName", "河南");
//
//                    //第三步，生成调用WebServices方法的SOAP请求信息，并制定SOAP的版本
//                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//                    //envelope.bodyOut = soapObject 等价 envelope.setOutputSoapObject(soapObject);
//                    envelope.bodyOut = soapObject;
//
//                    // 设置是否调用的是dotNet开发的WebService
//                    envelope.dotNet = true;
//
//                    //第四步，生成调用WebServices方法的HttpTransportSE实体对象
//                    HttpTransportSE transport = new HttpTransportSE(ServiceURL);
//
//                    //第五步，调用call方法调用WebService
//                    transport.call(nameSpace + methodName, envelope);
//
//                    //第六步，获取服务端返回的数据 soap
//                    SoapObject object = (SoapObject)envelope.bodyIn;
//
//                    //第七步，解析返回的soapObject并获取需要的数据结果
//                    String result = object.getProperty(0).toString();
//                    Log.d(TAG, "run: " + result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//
        HashMap<String, String> params = new HashMap<>();
        params.put("byProvinceName", "河南");
        WebServiceUtil.callWebService(ServiceURL, nameSpace, methodName, params, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(String result) {
                //第七步，解析返回的soapObject并获取需要的数据结果
                String string = result;
                Log.d(TAG, "run: " + string);
            }
        });

        Log.d(TAG, "initView: cpu = "+Runtime.getRuntime().availableProcessors());

    }

    @Override
    public void initData() {

    }
}
