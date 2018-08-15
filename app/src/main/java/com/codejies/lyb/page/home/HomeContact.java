package com.codejies.lyb.page.home;

import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.page.home.meizi.MeiziFragment;

/**
 * Created by Jies on 2018/7/20.
 */

public interface HomeContact {
    interface view extends BaseContact.baseView {
        MeiziFragment getMeiziFragment();

        void replaceFragment(int menuId);
    }

    interface model {

    }

    interface presenter extends BaseContact.basePresenter {
    }
}
