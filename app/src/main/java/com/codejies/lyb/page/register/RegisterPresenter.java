package com.codejies.lyb.page.register;

import com.codejies.lyb.base.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/6/6.
 */

public class RegisterPresenter  extends BasePresenter<RegisterContact.RegisterView> implements RegisterContact.RegisterPresenter {

    public RegisterPresenter(RegisterContact.RegisterView view) {
        super(view);
    }
}
