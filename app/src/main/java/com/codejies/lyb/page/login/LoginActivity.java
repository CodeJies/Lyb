package com.codejies.lyb.page.login;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;

import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
import com.codejies.lyb.page.register.RegisterActivity;
import com.codejies.lyb.utils.KeyboardUtil;
import com.codejies.lyb.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jies on 2018/5/11.
 */

public class LoginActivity extends BaseActivity<LoginContact.presenter> implements LoginContact.view{
    @BindView(R.id.login_phone)
    EditText et_phone;
    @BindView(R.id.login_password)
    EditText et_password;
    @BindView(R.id.root)
    ConstraintLayout root;
    KeyboardUtil mkeyboardUtils;
    @Override
    protected LoginContact.presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        mkeyboardUtils = new KeyboardUtil(this, root,et_password);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public String getPhone() {
        return StringUtils.noEmptyString(et_phone.getText().toString());
    }

    @Override
    public String getPassword() {
        return StringUtils.noEmptyString(et_password.getText().toString());
    }

    @OnClick({R.id.login_login,R.id.login_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_login:
                presenter.login();
                break;
            case R.id.login_register:
                Intent intent=new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if(mkeyboardUtils.isKeyboardShow()){
            mkeyboardUtils.hideKeyboard();
        }else{
            super.onBackPressed();
        }
    }
}
