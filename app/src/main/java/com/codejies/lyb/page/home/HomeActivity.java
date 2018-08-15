package com.codejies.lyb.page.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.page.home.meizi.MeiziFragment;
import com.codejies.lyb.widgets.LybMenuLayout;
import com.codejies.lybwidget.widget.draglayout.DragLayout;

import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity<HomeContact.presenter> implements DragLayout.DragListener, HomeContact.view, View.OnClickListener, LybMenuLayout.OnMenuItemClickLitener {
    @BindView(R.id.home_drawer)
    DragLayout drawerLayout;
    @BindView(R.id.home_top_drawer_open)
    ImageView drawerOpenIv;
    @BindView(R.id.home_menu_layout)
    LybMenuLayout menuLayout;
    @BindView(R.id.home_top_title)
    TextView titleTv;

    @BindView(R.id.home_content)
    FrameLayout contentLayout;


    String mCurrentFragmentTag;
    Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {
        setImmersiveStatusBar(true, ContextCompat.getColor(this, R.color.color_yellow));
        drawerOpenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        drawerLayout.setDragListener(this);
        menuLayout.setMeunItemClickListener(this);
    }


    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onDrag(float percent) {
        if (percent == 1) {
            drawerOpenIv.setImageResource(R.mipmap.icon_meun_select);
        } else if (percent == 0) {
            drawerOpenIv.setImageResource(R.mipmap.icon_meun_default);
        }

    }

    @Override
    public MeiziFragment getMeiziFragment() {
        return MeiziFragment.getInstance();
    }

    @Override
    public void replaceFragment(int MenuId) {
        switch (MenuId) {
            case R.id.item_meizi:
                titleTv.setText("Meizi");
                break;
            case R.id.item_setting:
                titleTv.setText("Setting");

                break;
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMenuItemClick(int id) {
        mCurrentFragmentTag = String.valueOf(id);
        boolean hasFragment = false;
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> list = manager.getFragments();
        FragmentTransaction transaction = manager.beginTransaction();
        if (list != null && list.size() > 0) {
            for (Fragment fragment : list) {
                String tag = String.valueOf(id);
                if (tag.equals(fragment.getTag())) {
                    hasFragment = true;
                    mCurrentFragment = fragment;
                    transaction.show(fragment);
                } else {
                    transaction.hide(fragment);
                }
            }
        }

        if (!hasFragment) {
            showHideFragment(id, transaction);
        } else {
            transaction.commit();
        }

        replaceFragment(id);
    }


    private void showHideFragment(int id, FragmentTransaction transaction) {
        Fragment fragment = null;
        switch (id) {
            case R.id.item_meizi:
                fragment = MeiziFragment.getInstance();
                break;
        }

        if (fragment == null) return;
        transaction.add(R.id.home_content, fragment, String.valueOf(id));
        transaction.commit();
    }
}
