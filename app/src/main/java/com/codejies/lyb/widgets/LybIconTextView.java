package com.codejies.lyb.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.codejies.lyb.R;


/**
 * Created by Jies on 2018/7/23.
 */

public class LybIconTextView extends android.support.v7.widget.AppCompatTextView {
    Drawable defaultIcon;
    Drawable hightLightIcon;
    int highLightColor;
    int defaultColor;
    int direction = 0;
    boolean isHightLight = true;//初始化为true 实例化的时候直接调用方法改为false

    public LybIconTextView(Context context) {
        this(context, null);
    }

    public LybIconTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LybIconTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LybIconTextView);
            for (int i = 0; i < array.getIndexCount(); i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.LybIconTextView_lybDefaultIcon:
                        defaultIcon = array.getDrawable(attr);
                        break;
                    case R.styleable.LybIconTextView_lybHighLightIcon:
                        hightLightIcon = array.getDrawable(attr);
                        break;
                    case R.styleable.LybIconTextView_direction:
                        direction = array.getInt(attr, 0);
                    case R.styleable.LybIconTextView_lybHighLightText:
                        highLightColor = array.getInt(attr, 0xfffffff);
                        break;
                    case R.styleable.LybIconTextView_lybDefalutText:
                        defaultColor = array.getInt(attr, 0xfffffff);
                        break;
                }
            }
            array.recycle();
        }
        setTextColor(defaultColor);
        setGravity(Gravity.CENTER_VERTICAL);
        changeIconState();
    }


    public void changeIconState() {
        if (isHightLight) {
            isHightLight = false;
        } else {
            isHightLight = true;
        }

        changeIconState(isHightLight);

    }

    public void changeIconState(boolean state){
        isHightLight=state;
        switch (direction) {
            case 0:
                if (isHightLight) {
                    setCompoundDrawablesWithIntrinsicBounds(hightLightIcon, null, null, null);
                    setTextColor(highLightColor);
                } else {
                    setTextColor(defaultColor);
                    setCompoundDrawablesWithIntrinsicBounds(defaultIcon, null, null, null);
                }
                break;
            case 1:
                if (isHightLight) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, hightLightIcon, null);
                    setTextColor(highLightColor);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, defaultIcon, null);
                    setTextColor(defaultColor);
                }
                break;
            case 2:
                if (isHightLight) {
                    setCompoundDrawablesWithIntrinsicBounds(null, hightLightIcon, null, null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, defaultIcon, null, null);
                }
                break;
            case 3:
                if (isHightLight) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, hightLightIcon);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, defaultIcon);
                }
                break;
        }
    }

}
