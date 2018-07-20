package com.codejies.lybwidget.widget.lybrecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codejies.lybwidget.R;
import com.codejies.lybwidget.widget.loading.LybLoadingProgress;

/**
 * Created by Jies on 2018/7/18.
 */

public class LybRefreshHeadView extends LinearLayout implements BaseRefreshHeader {
    Context mContext;
    LinearLayout mContainView;
    LybLoadingProgress mProgress;
    TextView mText;
    public int mMeasuredHeight;


    private int mState = BaseRefreshHeader.STATE_NORMAL;

    public LybRefreshHeadView(Context context) {
        this(context, null);
    }

    public LybRefreshHeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LybRefreshHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }


    private void init() {
        mContainView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.progress_head_layout, null);
        mProgress = mContainView.findViewById(R.id.progress);
        mText = mContainView.findViewById(R.id.text);
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mContainView, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();

    }

    public void destroy() {
        if (mProgress != null) {
            mProgress = null;
        }
    }

    public void setState(int state) {
        if (state == mState) return;
        if(state==STATE_REFRESHING) {
            smoothScrollTo(mMeasuredHeight);
        }
        switch (state) {
            case BaseRefreshHeader.STATE_NORMAL:
                mText.setText("下拉刷新");
                break;
            case BaseRefreshHeader.STATE_REFRESHING:
                mText.setText("刷新中");
                break;
            case BaseRefreshHeader.STATE_RELEASE_TO_REFRESH:
                mText.setText("松手即刷新");
                break;
            case BaseRefreshHeader.STATE_DONE:
                mText.setText("刷新完成");
                break;
        }
        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }


    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                reset();
            }
        }, 200);
    }


    //设置头部View的高度 可見
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainView.getLayoutParams();
        lp.height = height;
        mContainView.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainView.getLayoutParams();
        return lp.height;
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }

        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }


    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
