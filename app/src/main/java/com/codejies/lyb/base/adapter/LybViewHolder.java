package com.codejies.lyb.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jies on 2018/8/14.
 */

public class LybViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> viewSparseArray;

    private LybItemClickListener lybItemClickListener;

    public LybViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        viewSparseArray=new SparseArray<>();
    }


    public LybViewHolder setText(int viewId,CharSequence text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    public LybViewHolder setImageResouce(int viewId,int resouceId){
        ImageView imageView=getView(viewId);
        imageView.setImageResource(resouceId);
        return this;
    }

    public LybViewHolder setVisible(int viewId,int visible){
        getView(viewId).setVisibility(visible);
        return this;
    }

    public LybViewHolder setBackground(int viewId,int resource){
        getView(viewId).setBackgroundResource(resource);
        return this;
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }



    @Override
    public void onClick(View v) {
        if(lybItemClickListener!=null){
            lybItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}
