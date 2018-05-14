package com.codejies.lyb.page.splash;

import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.bean.SplashResponse;
import com.codejies.lyb.network.LybApiManager;

import io.reactivex.Observable;

/**
 * Created by Jies on 2018/5/10.
 */

public class SplashModel implements SplashContact.Model{
    @Override
    public Observable<BaseResult<SplashResponse>> getSplashData() {
        return LybApiManager.getApiService().getSplash();
    }
}
