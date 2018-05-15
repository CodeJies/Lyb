package com.codejies.lyb.page.splash;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.page.login.LoginActivity;

import butterknife.BindView;

/**
 * Created by Jies on 2018/5/10.
 */

public class SplashActivity  extends BaseActivity<SplashContact.Presenter> implements SplashContact.View {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_timer)
    TextView splashTimer;
    @Override
    protected SplashContact.Presenter initPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected void initView() {
        presenter.getSplashImage();
        presenter.jumpNext();
    }


    @Override
    public void displayPicture(String imageUrl) {
        Glide.with(this).load(imageUrl).into(splashImage);
    }

    @Override
    public void goNextActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setTimerText(String text) {
        splashTimer.setText(text);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }
}
