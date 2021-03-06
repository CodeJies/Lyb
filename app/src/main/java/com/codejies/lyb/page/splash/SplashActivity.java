package com.codejies.lyb.page.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.page.login.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jies on 2018/5/10.
 */

public class SplashActivity  extends BaseActivity<SplashContact.Presenter> implements SplashContact.View {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_timer)
    TextView splashTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
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
        finish();
    }

    @Override
    public void setTimerText(String text) {
        splashTimer.setText(text);
    }


    @OnClick({R.id.splash_timer})
    public void Onclick(View view) {
        goNextActivity();
    }
}
