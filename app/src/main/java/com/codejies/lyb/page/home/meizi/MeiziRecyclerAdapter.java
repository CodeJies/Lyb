package com.codejies.lyb.page.home.meizi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codejies.lyb.R;
import com.codejies.lyb.base.adapter.LybRecyclerViewAdapter;
import com.codejies.lyb.base.adapter.LybViewHolder;
import com.codejies.lyb.bean.MeiziResult;
import com.codejies.lyb.utils.ScreenTool;

import java.util.List;

/**
 * Created by Jies on 2018/8/14.
 */

public class MeiziRecyclerAdapter extends LybRecyclerViewAdapter<MeiziResult.Meizi> {
    public MeiziRecyclerAdapter(Context context, List<MeiziResult.Meizi> dataList, int layoutId) {

        super(context, dataList, layoutId);
    }

    @Override
    protected void bindData(final LybViewHolder holder, final  MeiziResult.Meizi data) {
        RequestOptions options=new RequestOptions().override(ScreenTool.getScreenWidth(mContext)/2,ScreenTool.getScreenWidth(mContext)/2);
        Glide.with(mContext).asBitmap().load(data.getPath()).apply(options).into((ImageView) holder.getView(R.id.item_home_meizi_iv));
        holder.getView(R.id.item_home_meizi_iv).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext,MeiziDetailActivity.class);
                intent.putExtra("url",data.getPath());
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                        holder.getView(R.id.item_home_meizi_iv),"showDetailMeizi").toBundle());

            }
        });
    }
}
