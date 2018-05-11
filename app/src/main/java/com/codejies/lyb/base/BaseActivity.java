package com.codejies.lyb.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;

/**
 * Created by Jies on 2018/5/10.
 */

public abstract class BaseActivity<P extends BaseContact.basePresenter> extends AppCompatActivity implements BaseContact.baseView{
    protected  P presenter;
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        context=this;
        presenter=initPresenter();
        initView();
        ButterKnife.bind(this);
    }


    protected abstract int setLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detach();
            presenter=null;
        }
    }


   protected abstract P initPresenter();

    protected abstract void initView();


    @Override
    public void showLoadingView() {
        Log.e("BASE","i am loding");
    }

    @Override
    public void hideLoadingView() {
        Log.e("BASE","loding is complete");

    }
}
