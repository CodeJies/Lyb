package com.codejies.lyb.page.login;

import android.util.Log;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.network.BaseObserve;

import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/5/11.
 */

public class LoginPresenter extends BasePresenter<LoginContact.view> implements LoginContact.presenter{
    LoginModel model;
    public LoginPresenter(LoginContact.view view) {
        super(view);
        model=new LoginModel();
    }

    @Override
    public void login() {
        model.login(new LoginRequest(view.getPhone(),view.getPassword())).compose(this.<BaseResult<LoginResponse>>Schedules()).subscribe(new BaseObserve<LoginResponse>(new BaseObserve.ResponseListener<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                Log.e("Login","success:"+loginResponse.toString());
                view.hideLoadingView();
            }

            @Override
            public void onFail(String error) {
                Log.e("Login","requestFail:"+error);
                view.showMessage(error);
//                view.hideLoadingView();
            }

            @Override
            public void onLoading(Disposable disposable) {
                view.showLoadingView();
                addDisposable(disposable);
                Log.e("Login","i am loading");
            }
        }));
    }
}
