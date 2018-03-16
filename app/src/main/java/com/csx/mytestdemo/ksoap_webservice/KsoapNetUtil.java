package com.csx.mytestdemo.ksoap_webservice;

import android.os.Message;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class KsoapNetUtil {
    SoapSerializationEnvelope envelope = null;
    String result = "error";

    /**
     * 发送网络请求
     *
     * @param wsdlUrl    wsdl文档的链接
     * @param methodName 请求的方法名称
     * @param params     请求参数
     */
    public void postRequest(String wsdlUrl,String spaceName, String methodName,
                            SoapObject params, Message msg) {

        //设置SOAP请求信息（参数部分为SOAP协议版本号，与你要调用到的WEBSERVICE中的版本号一致）
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = params;
        envelope.dotNet = true;
        //绑定参数
        envelope.setOutputSoapObject(params);
        //构建传输对象，并指明wsdl文档url
        HttpTransportSE httpTransportSE = new HttpTransportSE(wsdlUrl);
        try {
            //调用webservice（其中参数一SOAP_ACTION为  命名空间+方法名，参数二为envelope）
            httpTransportSE.call(spaceName + methodName, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //使用getResponse方法获得WebService方法的返回结果
            Object obj = envelope.getResponse();//结果：{"listData": [{"result":"true","UserRealName":"蔡文君","userLoginName":"31A"}]}
            if (obj != null) {
                result = obj2Json(obj);
                msg.obj = result;
            }
        } catch (SoapFault e) {
            Log.e("SoapFault", e.getMessage());
        }
        msg.sendToTarget();
    }

    /**
     * 转换成标准的字符串格式
     */
    private String obj2Json(Object result) {
        String str = "";
        if (result != null && !result.toString().trim().equals("") && result.toString().trim().contains("(")) {
            str = result.toString().trim();
            str = str.subSequence(str.indexOf('(') + 1, str.indexOf(')')).toString();
        } else if (result != null) {
            return result.toString().trim();
        }
        return str;
    }
}
