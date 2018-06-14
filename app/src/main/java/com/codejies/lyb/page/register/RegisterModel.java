package com.codejies.lyb.page.register;

import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.bean.RegisterRequest;
import com.codejies.lyb.bean.RegisterResponse;
import com.codejies.lyb.network.LybApiManager;

import io.reactivex.Observable;

/**
 * Created by Jies on 2018/6/12.
 */

public class RegisterModel implements RegisterContact.RegisterModel{
    @Override
    public Observable<BaseResult<RegisterResponse>> register(RegisterRequest request) {
        return LybApiManager.getApiService().register(request);
    }

    @Override
    public Observable<BaseResult<LoginResponse>> login(LoginRequest request) {
        return  LybApiManager.getApiService().login(request);
    }
}
