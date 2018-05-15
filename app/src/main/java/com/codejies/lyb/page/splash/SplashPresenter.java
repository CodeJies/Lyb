package com.codejies.lyb.page.splash;

import android.util.Log;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.SplashResponse;
import com.codejies.lyb.network.BaseObserve;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Jies on 2018/5/10.
 */

public class SplashPresenter extends BasePresenter<SplashContact.View> implements SplashContact.Presenter{
    SplashModel model;
    public SplashPresenter(SplashContact.View view) {
        super(view);
        this.model=new SplashModel();
    }

    @Override
    public void getSplashImage() {
        model.getSplashData().compose(this.<BaseResult<SplashResponse>>Schedules()).subscribe(new BaseObserve<SplashResponse>(new BaseObserve.ResponseListener<SplashResponse>() {
            @Override
            public void onSuccess(SplashResponse splashResponse) {
                view.displayPicture(splashResponse.getUrl());
            }

            @Override
            public void onFail(String error) {

            }

            @Override
            public void onLoading(Disposable d) {
                addDisposable(d);
            }
        }));
    }

    @Override
    public void jumpNext() {
        Observable.interval(0, 1, TimeUnit.SECONDS).compose(this.<Long>Schedules()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(Long aLong) {
                Log.e("Tag", aLong + "");
                int defaultTime = 5;
                Long countTime = defaultTime - aLong;
                if (countTime > 0) {
                    view.setTimerText(countTime+"s \t跳过");
                }else{
                    view.goNextActivity();
                    onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                detach();
            }
        });
    }
}
