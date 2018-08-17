package com.codejies.lyb.page.home.meizi;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.widgets.LybFullFitImageView;

import butterknife.BindView;

public class MeiziDetailActivity extends BaseActivity {
    @BindView(R.id.meizi_detail_iv)
    LybFullFitImageView detailIv;
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
        Glide.with(this).load(getIntent().getStringExtra("url")).into(detailIv);
    }
}
