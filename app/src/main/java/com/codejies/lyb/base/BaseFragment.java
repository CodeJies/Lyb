package com.codejies.lyb.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codejies.lyb.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by Jies on 2018/8/14.
 */

public abstract class BaseFragment<P extends BaseContact.basePresenter> extends Fragment implements BaseContact.baseView {
    protected View root;

    protected P presenter;

    protected Dialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(setLayout(), null);
        ButterKnife.bind(this, root);
        presenter = initPresenter();
        initView();
        return root;

    }

    protected abstract P initPresenter();

    protected abstract void initView();

    protected int setLayout() {
        return 0;
    }

    @Override
    public void showLoadingView() {
        Log.e("BASE", "i am loding");
        if (mLoadingDialog == null && getActivity() != null && !getActivity().isFinishing()) {
            mLoadingDialog = new LoadingDialog.Builder(getActivity()).create();
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && getActivity() != null && !getActivity().isFinishing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog = null;
        }

    }
}
