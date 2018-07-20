package com.codejies.lyb.base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.codejies.lyb.R;
import com.codejies.lyb.dialog.LoadingDialog;
import com.codejies.lyb.utils.OsUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Jies on 2018/5/10.
 */

public abstract class BaseActivity<P extends BaseContact.basePresenter> extends SwipeBackActivity implements BaseContact.baseView {
    protected P presenter;
    public Context context;
    View viewStatusBarPlace;//頭部的statusBar
    FrameLayout frameLayoutContentPlace;//需要填充的佈局
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_compat_status_bar);
        context = this;
        presenter = initPresenter();
        initBaseView();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
        if (mLoadingDialog != null) {
            mLoadingDialog = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out);
    }


    protected abstract P initPresenter();

    protected abstract void initView();

    private void initBaseView() {
        viewStatusBarPlace=findViewById(R.id.view_status_bar_place);
        frameLayoutContentPlace=(FrameLayout) findViewById(R.id.frame_layout_content_place);
        //设置状态栏高度
        ViewGroup.LayoutParams params = viewStatusBarPlace.getLayoutParams();
        params.height = getStatusBarHeight();
        viewStatusBarPlace.setLayoutParams(params);
    }

    @Override
    public void showLoadingView() {
        Log.e("BASE", "i am loding");
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog.Builder(this).create();
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
        Log.e("BASE", "loding is complete");

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //获取状态栏高度
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        frameLayoutContentPlace.addView(contentView);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    protected void setImmersiveStatusBar(boolean fontIconDark, int statusBarPlaceColor) {
        setTranslucentStatus();
        if (fontIconDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    || OsUtil.isMIUI()
                    || OsUtil.isFlyme()) {
                setStatusBarFontIconDark(true);
            } else {
                if (statusBarPlaceColor == Color.WHITE) {
                    statusBarPlaceColor = 0xffcccccc;
                }
            }
        }
        setStatusBarPlaceColor(statusBarPlaceColor);
    }

    private void setStatusBarPlaceColor(int statusColor) {
        if (viewStatusBarPlace != null) {
            viewStatusBarPlace.setBackgroundColor(statusColor);
        }
    }


    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {

        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setStatusBarFontIconDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    // 设置app字体不随系统字体设置改变
    @Override
    public Resources getResources() {
        Resources mResources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        mResources.updateConfiguration(config, mResources.getDisplayMetrics());
        return mResources;
    }
}
