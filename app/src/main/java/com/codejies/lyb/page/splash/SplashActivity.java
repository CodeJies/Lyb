package com.codejies.lyb.page.splash;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Jies on 2018/5/10.
 */

public class SplashActivity  extends BaseActivity<SplashContact.Presenter> implements SplashContact.View {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @Override
    protected SplashContact.Presenter initPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected void initView() {
        presenter.getSplashImage();
    }


    @Override
    public void displayPicture(String imageUrl) {
        Glide.with(this).load(imageUrl).into(splashImage);
    }

    @Override
    public void goNextActivity() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }
}
