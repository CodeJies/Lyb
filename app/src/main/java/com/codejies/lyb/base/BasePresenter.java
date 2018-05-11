package com.codejies.lyb.base;

import android.view.View;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/5/10.
 */

public abstract class BasePresenter<V extends BaseContact.baseView> implements  BaseContact.basePresenter{
    protected  V view;

    private CompositeDisposable mCompositeDisposable;
    public BasePresenter(V view){
        this.view=view;
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void detach() {
        this.view=null;
        unDisposable();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if(mCompositeDisposable==null||mCompositeDisposable.isDisposed()){
            mCompositeDisposable=new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unDisposable() {
        if(mCompositeDisposable!=null){
            mCompositeDisposable.dispose();
        }
    }
}
