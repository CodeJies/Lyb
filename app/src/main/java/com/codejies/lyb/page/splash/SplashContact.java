package com.codejies.lyb.page.splash;

import com.codejies.lyb.base.BaseContact;

/**
 * Created by Jies on 2018/5/10.
 */

public interface SplashContact {
    interface Presenter extends BaseContact.basePresenter{
        void getSplashImage();
    }

    interface Model{

    }

    interface View extends BaseContact.baseView{
        void displayPicture(String imageUrl);
        void goNextActivity();
    }
}
