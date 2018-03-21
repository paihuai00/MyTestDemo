package com.csx.mytestdemo.rxjava_test;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Created by cuishuxiang
 * @date 2018/3/13.
 * @description:
 */

public interface ApiService {

    /**
     * http://api.juheapi.com/japi/toh?
     * key=8bd460e1802adb477698a4a2bae65cde&
     * v=1.0&
     * month=11&
     * day=1
     */
    @GET("{id}/toh")
    Observable<NewsBean> getUserInfo(@Path("id") String japi,
                                     @Query("key") String key,
                                     @Query("v") String v,
                                     @Query("month") String month,
                                     @Query("day") String day);

}
