package com.codejies.lyb.page.splash;

import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.SplashResponse;

import io.reactivex.Observable;

/**
 * Created by Jies on 2018/5/10.
 */

public interface SplashContact {
    interface Presenter extends BaseContact.basePresenter{
        void getSplashImage();
        void jumpNext();
    }

    interface Model{
        Observable<BaseResult<SplashResponse>> getSplashData();
    }

    interface View extends BaseContact.baseView{
        void displayPicture(String imageUrl);
        void goNextActivity();
    }
}
