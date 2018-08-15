package com.codejies.lyb.page.home.meizi;

import android.util.Log;

import com.codejies.lyb.base.BasePresenter;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.network.BaseObserve;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Jies on 2018/8/14.
 */

public class MeiziPresenter extends BasePresenter<MeiziContact.view> implements MeiziContact.presenter{
    MeiziModel model;
    public MeiziPresenter(MeiziContact.view view) {
        super(view);
        model=new MeiziModel();
    }

    @Override
    public void getMeiziData(int pageIndex) {
        model.getMeiziList(pageIndex).compose(this.<BaseResult<List<String>>>Schedules()).subscribe(new BaseObserve<List<String>>(new BaseObserve.ResponseListener<List<String>>() {
            @Override
            public void onSuccess(List<String> homeMeiziResults) {
                view.getRecycleView().refreshComplete();
                view.getRecycleView().loadMoreComplete();
                view.getAdapter().addList(homeMeiziResults);
            }

            @Override
            public void onFail(String error) {
                Log.e(error,error);
                view.getRecycleView().refreshComplete();
                view.getRecycleView().loadMoreComplete();
            }

            @Override
            public void onLoading(Disposable d) {

            }
        }));
    }
}
