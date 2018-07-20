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
import io.reactivex.ObservableSource;
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



        model.register(new RegisterRequest(view.getPhone(), view.getPassword()))
                .compose(this.<BaseResult<RegisterResponse>>Schedules())
                .flatMap(new Function<BaseResult<RegisterResponse>, ObservableSource<BaseResult<LoginResponse>>>() {
                    @Override
                    public ObservableSource<BaseResult<LoginResponse>> apply(BaseResult<RegisterResponse> registerResponseBaseResult) throws Exception {
                        if(registerResponseBaseResult.getErrorCode()==0){
                            return model.login(new LoginRequest(view.getPhone(),view.getPassword())).compose(RegisterPresenter.this.<BaseResult<LoginResponse>>Schedules());
                        }else{
                            return null;
                        }
                    }
                }).subscribe(new BaseObserve<>(new BaseObserve.ResponseListener<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                Log.e("LOGIN","success");
            }

            @Override
            public void onFail(String error) {
                Log.e("LOGIN","fail");

            }

            @Override
            public void onLoading(Disposable d) {
                Log.e("LOGIN","loading");

            }
        }));

    }
}
