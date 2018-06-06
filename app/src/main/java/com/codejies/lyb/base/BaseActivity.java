package com.codejies.lyb.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.codejies.lyb.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by Jies on 2018/5/10.
 */

public abstract class BaseActivity<P extends BaseContact.basePresenter> extends AppCompatActivity implements BaseContact.baseView {
    protected P presenter;
    public Context context;


    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        context = this;
        presenter = initPresenter();
        initBaseView();
        ButterKnife.bind(this);
        initView();
    }


    protected abstract int setLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog = null;
        }
    }


    protected abstract P initPresenter();

    protected abstract void initView();

    private void initBaseView() {
    }

    @Override
    public void showLoadingView() {
        Log.e("BASE", "i am loding");
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog.Builder(this).create();
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        Log.e("BASE", "loding is complete");

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
