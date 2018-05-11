package com.codejies.lyb.page.login;

import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.network.LybApiManager;

import io.reactivex.Observable;

/**
 * Created by Jies on 2018/5/11.
 */

public class LoginModel implements LoginContact.model {

    Observable<BaseResult<LoginResponse>> login(LoginRequest request){
       return LybApiManager.getApiService().login(request);
    }
}
