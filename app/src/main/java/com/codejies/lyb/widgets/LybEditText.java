package com.codejies.lyb.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.codejies.lyb.R;

/**
 * Created by Jies on 2018/5/16.
 */

public class LybEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private static final int TYPE_PHONE = 0;
    private static final int TYPE_PASSWORD = 1;

    private int type;

    private onTextChangeErrorListener ErrorListener;

    public LybEditText(Context context) {
        super(context);
    }

    public LybEditText(Context context, AttributeSet attrs) {
        super(context,attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LybEditText);
        type = typedArray.getInt(R.styleable.LybEditText_type, 0);
        typedArray.recycle();
    }

    public LybEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LybEditText);
        type = typedArray.getInt(R.styleable.LybEditText_type, 0);
        typedArray.recycle();
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = TextUtils.isEmpty(s) ? "" : s.toString();
        String error = "";
        switch (type) {
            case TYPE_PHONE:
                if(text.length()<=0){
                    error="手机号码不能为空";
                }else if (text.length() < 11) {
                    error="请输入11位的手机号";
                }
                break;
            case TYPE_PASSWORD:
                if(text.length()<=0){
                    error="密码不能为空";
                }else if(text.length()<6){
                    error="密码不能少于6位";
                }
                break;
        }

        if (!TextUtils.isEmpty(error)) {
            ErrorListener.sendErrorMsg(error);
        } else {
            ErrorListener.vaildSuccess();
        }
    }



    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    public void setChangeErrorListener(onTextChangeErrorListener listener){
        this.ErrorListener=listener;
        addTextChangedListener(this);
    }

    public interface onTextChangeErrorListener {
        //发送错误信息给外部
        void sendErrorMsg(String error);

        //成功通过验证需要的
        void vaildSuccess();
    }
}
