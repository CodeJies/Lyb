package com.codejies.lyb.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jies on 2018/8/14.
 */

public abstract class LybRecyclerViewAdapter<T> extends RecyclerView.Adapter<LybViewHolder> {
    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    protected LybMultiTypeSupport<T> multiTypeSupport;
    protected Context mContext;


    public LybRecyclerViewAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
        this.mContext=context;
    }

    @Override
    public int getItemViewType(int position) {
        if (multiTypeSupport != null) {
            return multiTypeSupport.getLayoutId(dataList.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public LybViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (multiTypeSupport != null) {
            layoutId = viewType;
        }
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new LybViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LybViewHolder holder, int position) {
        bindData(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void addList(List<T> data){
        if(data!=null){
            this.dataList.addAll(data);
            this.notifyDataSetChanged();
        }
    }
    protected abstract void bindData(LybViewHolder holder, T data);

}
