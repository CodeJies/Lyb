package com.codejies.lyb.bean;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Jies on 2018/5/11.
 */

public class RequestEntity<T>  {
    String requestJson;
    public RequestEntity(T requestEntity){
        Gson gson=new Gson();
        requestJson=gson.toJson(requestEntity);
    }

    RequestBody getRequestBody(){
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"),requestJson);
    }
}
