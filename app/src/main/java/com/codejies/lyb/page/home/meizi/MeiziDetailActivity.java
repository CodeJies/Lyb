package com.codejies.lyb.page.home.meizi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.page.splash.SplashContact;
import com.codejies.lyb.utils.ScreenTool;
import com.codejies.lyb.widgets.LybFullFitImageView;
import com.codejies.lybwidget.widget.popupwindows.LybPopupwindows;

import butterknife.BindView;
import butterknife.OnClick;

public class MeiziDetailActivity extends BaseActivity {
    @BindView(R.id.meizi_detail_iv)
    LybFullFitImageView detailIv;
    @BindView(R.id.root)
    ConstraintLayout root;

    //底部彈窗
    LybPopupwindows moreDetailPopupwindows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_detail);
    }

    @Override
    protected BaseContact.basePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        setImmersiveStatusBar(true, ContextCompat.getColor(this, R.color.color_yellow));
        if (getIntent().getStringExtra("url").contains(".gif")) {
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(this).asGif().apply(options).load(getIntent().getStringExtra("url")).into(detailIv);
        } else {
            Glide.with(this).load(getIntent().getStringExtra("url")).into(detailIv);
        }
    }


    public void initPopupwindows() {
        //测量View的宽高
        View popupwindows = LayoutInflater.from(this).inflate(R.layout.popup_meizi_detail_bottom, null);
        ScreenTool.measureWidthAndHeight(popupwindows);
        moreDetailPopupwindows = new LybPopupwindows.Builder(this).
                setView(R.layout.popup_meizi_detail_bottom)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.AnimalBottom)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new LybPopupwindows.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
                        view.findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("popup", "downLoad");
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();

    }

    public static void openThisActivity(Context context, String imageUrl, View view) {
        Intent intent = new Intent();
        intent.setClass(context, MeiziDetailActivity.class);
        intent.putExtra("url", imageUrl);
        if (Build.VERSION.SDK_INT >= 21) {
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    view, "showDetailMeizi").toBundle());
        } else {
            context.startActivity(intent);
        }
    }


    @OnClick(R.id.meizi_detail_iv)
    public void OnClick(View view) {
        if (view.getId() == R.id.meizi_detail_iv) {
            if (moreDetailPopupwindows == null) {
                initPopupwindows();
            }
            moreDetailPopupwindows.showAtLocation(root, Gravity.BOTTOM, 0, 0);
        }
    }
}
