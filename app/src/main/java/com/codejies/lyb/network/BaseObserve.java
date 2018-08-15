package com.codejies.lyb.network;

import android.util.Log;

import com.codejies.lyb.bean.BaseResult;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/5/11.
 */

public class BaseObserve<T> implements Observer<BaseResult<T>> {
    ResponseListener<T> responseListener;

    public BaseObserve(ResponseListener<T> responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        responseListener.onLoading(d);
    }

    @Override
    public void onNext(BaseResult<T> t) {
        Log.e("RequestResult", new Gson().toJson(t));
        if (t.getCode() == 200) {
            responseListener.onSuccess((T) t.getResult());
        } else {
            responseListener.onFail(t.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        responseListener.onFail(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public interface ResponseListener<T> {
        void onSuccess(T t);

        void onFail(String error);

        void onLoading(Disposable d);
    }
}
