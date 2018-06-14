package com.codejies.lyb.page.register;

import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.bean.RegisterRequest;
import com.codejies.lyb.bean.RegisterResponse;

import io.reactivex.Observable;

/**
 * Created by Jies on 2018/5/16.
 */

public interface RegisterContact {
    interface RegisterView extends BaseContact.baseView {
        String getPhone();
        String getPassword();

        void showTips(String text);

    }

    interface RegisterModel {
        Observable<BaseResult<RegisterResponse>> register(RegisterRequest request);

        Observable<BaseResult<LoginResponse>> login(LoginRequest request);

    }

    interface RegisterPresenter extends BaseContact.basePresenter {
        void Register();
    }
}
