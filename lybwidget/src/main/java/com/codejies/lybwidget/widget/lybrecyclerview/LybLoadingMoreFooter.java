package com.codejies.lybwidget.widget.lybrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codejies.lybwidget.R;
import com.codejies.lybwidget.widget.loading.LybLoadingProgress;

public class LybLoadingMoreFooter extends LinearLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    private LybLoadingProgress progressView;

    private RelativeLayout mConstainView;
    private TextView mText;

	public LybLoadingMoreFooter(Context context) {
		super(context);
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LybLoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void destroy(){
	    if(progressView != null){
            progressView = null;
        }
    }

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mConstainView= (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.progress_foot_layout,null);
        mText=mConstainView.findViewById(R.id.foot_text);
        progressView=mConstainView.findViewById(R.id.foot_progress);
        addView(mConstainView);
        mConstainView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mText.setText(getContext().getText(R.string.recycle_loading));

        if(loadingHint == null || loadingHint.equals("")){
            loadingHint = (String)getContext().getText(R.string.recycle_loading);
        }
        if(noMoreHint == null || noMoreHint.equals("")){
            noMoreHint = (String)getContext().getText(R.string.recycle_loading_nodata);
        }
        if(loadingDoneHint == null || loadingDoneHint.equals("")){
            loadingDoneHint = (String)getContext().getText(R.string.recycle_loading_done);
        }
    }


    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                progressView.setVisibility(View.VISIBLE);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(noMoreHint);
                progressView.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
