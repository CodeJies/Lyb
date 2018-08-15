package com.codejies.lyb.page.home;

import android.util.Log;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.bean.HomeMeiziResult;
import com.codejies.lyb.network.BaseObserve;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/8/14.
 */

public class HomePresenter extends BasePresenter<HomeContact.view> implements HomeContact.presenter{
    HomeModel model;
    public HomePresenter(HomeContact.view view) {
        super(view);
        model=new HomeModel();
    }

}
