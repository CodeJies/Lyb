package com.codejies.lyb.page.home.meizi;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;

import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseFragment;
import com.codejies.lyb.bean.MeiziResult;
import com.codejies.lybwidget.widget.LybRecycleView;
import com.codejies.lybwidget.widget.lybrecyclerview.LybLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Jies on 2018/8/14.
 */

public class MeiziFragment extends BaseFragment<MeiziContact.presenter> implements MeiziContact.view, LybLoadingListener {
    @BindView(R.id.meizi_recycleView)
    LybRecycleView recycleView_meizi;

    MeiziRecyclerAdapter adapter;

    List<MeiziResult.Meizi> datas = new ArrayList<>();
    int index = 1;

    static MeiziFragment fragment = null;

    @Override
    protected void initView() {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recycleView_meizi.setLayoutManager(manager);
        recycleView_meizi.setLybLoadingListener(this);
        adapter = new MeiziRecyclerAdapter(getContext(), datas, R.layout.item_home_meizi);
        recycleView_meizi.setAdapter(adapter);
        onRefresh();
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_meizi;
    }

    @Override
    public LybRecycleView getRecycleView() {
        return recycleView_meizi;
    }

    @Override
    public MeiziRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected MeiziContact.presenter initPresenter() {
        return new MeiziPresenter(this);
    }

    @Override
    public void onRefresh() {
        index = 1;
        presenter.getMeiziData(index);
    }

    @Override
    public void onLoadMore() {
        index++;
        presenter.getMeiziData(index);
    }


    public static MeiziFragment getInstance() {
        if (fragment == null) {
            fragment = new MeiziFragment();
        }
        return fragment;
    }
}
