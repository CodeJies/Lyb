package com.codejies.lyb.page.register;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.widgets.LybEditText;

import butterknife.BindView;

/**
 * Created by Jies on 2018/6/6.
 */

public class RegisterActivity extends BaseActivity<RegisterContact.RegisterPresenter> implements RegisterContact.RegisterView{
    @BindView(R.id.register_phone_et)
    LybEditText et_phone;
    @BindView(R.id.register_password_et)
    LybEditText et_password;
    @BindView(R.id.register_commit)
    Button btn_commit;
    @BindView(R.id.register_tips_tv)
    TextView tv_tips;
    boolean isPhoneTrue;
    boolean isPasswordTrue;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterContact.RegisterPresenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initView() {
        et_password.setChangeErrorListener(new LybEditText.onTextChangeErrorListener() {
            @Override
            public void sendErrorMsg(String error) {
                showTips(error);
                isPasswordTrue=false;
            }

            @Override
            public void vaildSuccess() {
                isPasswordTrue=true;
                showTips("");
                btn_commit.setEnabled(isPasswordTrue&&isPhoneTrue?true:false);
            }
        });
        et_phone.setChangeErrorListener(new LybEditText.onTextChangeErrorListener() {
            @Override
            public void sendErrorMsg(String error) {
//                showMessage(error);
                showTips(error);
                isPhoneTrue=false;
            }

            @Override
            public void vaildSuccess() {
                isPhoneTrue=true;
                showTips("");
                btn_commit.setEnabled(isPasswordTrue&&isPhoneTrue?true:false);
            }
        });
    }

    @Override
    public String getPhone() {
        return et_phone.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void showTips(String text) {
        if(TextUtils.isEmpty(text)){
            tv_tips.setVisibility(View.GONE);
            tv_tips.setText("");
        }else{
            tv_tips.setVisibility(View.VISIBLE);
            tv_tips.setText(text);
        }

    }
}
