package com.codejies.lyb.page.login;

import android.view.View;
import android.widget.EditText;

import com.codejies.lyb.R;
import com.codejies.lyb.base.BaseActivity;
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
    @Override
    protected LoginContact.presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.login_login})
    public void onClick(View view){
        presenter.login();
    }
}
