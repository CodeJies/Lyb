package com.codejies.lyb.page.login;

import android.util.Log;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.network.BaseObserve;

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
<<<<<<< HEAD
        model.login(new LoginRequest(view.getPhone(),view.getPassword())).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new BaseObserve<LoginResponse>(new BaseObserve.ResponseListener<LoginResponse>() {
=======
        model.login(new LoginRequest(view.getPhone(),view.getPassword())).compose(this.<BaseResult<LoginResponse>>Schedules()).subscribe(new BaseObserve<LoginResponse>(new BaseObserve.ResponseListener<LoginResponse>() {
>>>>>>> faeea73f708318628a7e9ebae47b922b37813c31
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                Log.e("Login","success:"+loginResponse.toString());

            }

            @Override
            public void onFail(String error) {
                Log.e("Login","requestFail:"+error);
                view.showMessage(error);
            }

            @Override
            public void onLoading() {
                view.showLoadingView();
                Log.e("Login","i am loading");
            }
        }));
    }
}
