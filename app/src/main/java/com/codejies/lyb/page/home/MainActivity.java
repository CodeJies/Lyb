package com.codejies.lyb.page.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.widgets.LybEditText;
import com.codejies.lybwidget.widget.LybRecycleView;
import com.codejies.lybwidget.widget.lybrecyclerview.LybLoadingListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements LybLoadingListener {
    @BindView(R.id.main_list)
    LybRecycleView list;
    LybEditText edittext;
    ArrayList<String> datas = new ArrayList<>();
    MyAdapter myAdapter;
    int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected BaseContact.basePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(manager);
        list.setLybLoadingListener(this);
        for (int i = 0; i < 10; i++) {
            datas.add("item:" + i);
        }
        View editView=LayoutInflater.from(this).inflate(R.layout.home_test_edit,null);
        list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.dialog_loading,null));
        list.addHeaderView(editView);
        edittext=editView.findViewById(R.id.edittext);
        myAdapter = new MyAdapter(datas);
        list.setAdapter(myAdapter);
        edittext.setChangeErrorListener(new LybEditText.onTextChangeErrorListener() {
            @Override
            public void sendErrorMsg(String error) {
                Toast.makeText(MainActivity.this,"error"+error,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void vaildSuccess() {

            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                datas.clear();
                for (int i = 0; i < 10; i++) {
                    datas.add("item" + i + "after " + 111 + " times of refresh");
                }
                myAdapter.notifyDataSetChanged();
                if (list != null)
                    list.refreshComplete();
            }

        }, 3000);            //refresh data here
    }

    @Override
    public void onLoadMore() {
        Log.e("aaaaa", "call onLoadMore");
        if (times < 2) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        datas.add("item" + (1 + datas.size()));
                    }
                    if (list != null) {
                        list.loadMoreComplete();
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }, 3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        datas.add("item" + (1 + datas.size()));
                    }
                    if (list != null) {
                        list.setNoMore(true);
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }, 1000);
        }
        times++;
    }
}
