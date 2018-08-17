package com.codejies.lyb.network;

import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.HomeMeiziResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.bean.MeiziResult;
import com.codejies.lyb.bean.RegisterRequest;
import com.codejies.lyb.bean.RegisterResponse;
import com.codejies.lyb.bean.SplashDataResult;
import com.codejies.lyb.bean.SplashResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Jies on 2018/5/9.
 */

public interface ApiService {

    @POST("user/register")
    Observable<BaseResult<RegisterResponse>> register(@Body RegisterRequest info);

    @POST("user/login")
    Observable<BaseResult<LoginResponse>> login(@Body LoginRequest info);

    @POST("app/splash")
    Observable<BaseResult<SplashResponse>> getSplash();

    @FormUrlEncoded
    @POST("jiandan/jiandan/imgList")
    Observable<BaseResult<MeiziResult>> getMeiziList(@Field("page") int index);
}
