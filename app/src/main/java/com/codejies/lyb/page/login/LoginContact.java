package com.codejies.lyb.page.login;

import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.base.BasePresenter;

/**
 * Created by Jies on 2018/5/11.
 */

public interface LoginContact {
    interface presenter extends BaseContact.basePresenter{
        void login();
    }

    interface model{

    }

    interface view extends BaseContact.baseView{
        String getPhone();
        String getPassword();

    }
}
