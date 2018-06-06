package com.codejies.lyb.page.register;

import com.codejies.lyb.base.BaseContact;

/**
 * Created by Jies on 2018/5/16.
 */

public interface RegisterContact {
    interface RegisterView extends BaseContact.baseView {
        String getPhone();
        String getPassword();

        void showTips(String text);

    }

    interface RegisterModel {

    }

    interface RegisterPresenter extends BaseContact.basePresenter {

    }
}
