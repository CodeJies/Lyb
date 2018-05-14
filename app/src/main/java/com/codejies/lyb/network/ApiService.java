package com.codejies.lyb.network;

import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.bean.SplashDataResult;
import com.codejies.lyb.bean.SplashResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Jies on 2018/5/9.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResult<SplashDataResult>> getSplashData();

    @POST("user/login")
    Observable<BaseResult<LoginResponse>> login(@Body LoginRequest info);

    @POST("app/splash")
    Observable<BaseResult<SplashResponse>> getSplash();
}
