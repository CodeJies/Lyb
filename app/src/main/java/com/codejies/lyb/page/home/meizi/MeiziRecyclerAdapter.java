package com.codejies.lyb.page.home.meizi;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codejies.lyb.R;
import com.codejies.lyb.base.adapter.LybRecyclerViewAdapter;
import com.codejies.lyb.base.adapter.LybViewHolder;
import com.codejies.lyb.bean.HomeMeiziResult;
import com.codejies.lyb.utils.ScreenTool;

import java.util.List;

/**
 * Created by Jies on 2018/8/14.
 */

public class MeiziRecyclerAdapter extends LybRecyclerViewAdapter<String> {
    public MeiziRecyclerAdapter(Context context, List<String> dataList, int layoutId) {

        super(context, dataList, layoutId);
    }

    @Override
    protected void bindData(LybViewHolder holder, String data) {
        RequestOptions options=new RequestOptions().override(ScreenTool.getScreenWidth(mContext)/2,ScreenTool.getScreenWidth(mContext)/2);
        Glide.with(mContext).asBitmap().load(data).apply(options).into((ImageView) holder.getView(R.id.item_home_meizi_iv));
    }
}
