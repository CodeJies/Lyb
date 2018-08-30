package com.codejies.lyb.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jies on 2018/5/9.
 */

public class LybApiManager {
    private Retrofit retrofit;
//    private static final String BASEURL="http://10.0.2.2/";
    private static final long DEFAULTTIMEOUT = 100000;
    private static final String BASEURL="http://192.168.3.8/";

    private ApiService apiService;

    public LybApiManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                readTimeout(DEFAULTTIMEOUT, TimeUnit.MILLISECONDS).
                connectTimeout(DEFAULTTIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder().baseUrl(BASEURL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        apiService = retrofit.create(ApiService.class);
    }


    public static class singletonHolder {
        public static final LybApiManager INSTANCE = new LybApiManager();
    }

    public static ApiService getApiService() {
        return singletonHolder.INSTANCE.apiService;
    }
}
