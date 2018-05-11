package com.codejies.lyb.base;

import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/5/10.
 */

public interface BaseContact {
    interface basePresenter{
        void start();
        void detach();
        void addDisposable(Disposable disposable);
        void unDisposable();
    }

    interface baseView{
        void showLoadingView();
        void hideLoadingView();
    }

}
