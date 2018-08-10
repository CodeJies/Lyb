package com.codejies.lyb.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.codejies.lyb.R;

/**
 * Created by Jies on 2018/8/10.
 */

public class LybMenuLayout extends LinearLayout implements View.OnClickListener {
    ConstraintLayout view;

    public LybMenuLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LybMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void initView() {
        view = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.meun_drawer_layout, null);
        addView(view);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setOnClickListener(this);
            if(view.getChildAt(i).getId()==R.id.item_story){
                onClick(view.getChildAt(i));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof LybIconTextView) {
            for(int i=0;i<view.getChildCount();i++){
                if(view.getChildAt(i) instanceof LybIconTextView){
                    if(view.getChildAt(i).getId()==v.getId()){
                        ((LybIconTextView) view.getChildAt(i)).changeIconState(true);
                    }else{
                        ((LybIconTextView) view.getChildAt(i)).changeIconState(false);
                    }
                }
            }
        }

        if (itemListener != null) {
            itemListener.onClick(v);
        }
    }

    OnClickListener itemListener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.itemListener = listener;
    }


}
