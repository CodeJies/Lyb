package com.codejies.lyb.page.splash;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.SplashResponse;
import com.codejies.lyb.network.BaseObserve;

import java.util.Observable;

import io.reactivex.Observer;

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
            public void onLoading() {

            }
        }));
    }

    @Override
    public void jumpNext() {
    }
}
