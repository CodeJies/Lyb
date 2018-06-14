package com.codejies.lyb.page.register;

import android.util.Log;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.LoginRequest;
import com.codejies.lyb.bean.LoginResponse;
import com.codejies.lyb.bean.RegisterRequest;
import com.codejies.lyb.bean.RegisterResponse;
import com.codejies.lyb.network.BaseObserve;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Jies on 2018/6/6.
 */

public class RegisterPresenter  extends BasePresenter<RegisterContact.RegisterView> implements RegisterContact.RegisterPresenter {
    RegisterModel model;
    public RegisterPresenter(RegisterContact.RegisterView view) {
        super(view);
        model=new RegisterModel();
    }

    public void Register(){
        model.register(new RegisterRequest(view.getPhone(),view.getPassword())).compose(this.<BaseResult<RegisterResponse>>Schedules()).map(new Function<Observable<BaseResult<RegisterResponse>>, Observable<BaseResult<LoginResponse>>>() {
            @Override
            public Observable<BaseResult<LoginResponse>> apply(Observable<BaseResult<RegisterResponse>> baseResultObservable) throws Exception {
                return null;
            }
        }).subscribe(new BaseObserve<LoginResponse>(new BaseObserve.ResponseListener<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {

            }

            @Override
            public void onFail(String error) {

            }

            @Override
            public void onLoading(Disposable d) {

            }
        }) {

        });
    }
}
