package com.csx.mytestdemo.app;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Create by cuishuxiang
 *
 * @date: on 2018/9/18
 * @description: 高德定位
 */
public class LocationUtil {
    public static AMapLocationClient getLocation(Context context, final LocationListener listener) {

        AMapLocationClient client = new AMapLocationClient(context);
        client.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                listener.onLocation(aMapLocation);
            }
        });
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocationLatest(true);
        option.setNeedAddress(true);
        client.setLocationOption(option);
        client.startLocation();
        return client;
    }

    public interface LocationListener {
        void onLocation(AMapLocation location);
    }
}
