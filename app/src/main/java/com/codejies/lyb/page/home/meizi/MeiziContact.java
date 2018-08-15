package com.codejies.lyb.page.home.meizi;

import com.codejies.lyb.base.BaseContact;
import com.codejies.lyb.bean.BaseResult;
import com.codejies.lybwidget.widget.LybRecycleView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Jies on 2018/8/14.
 */

public interface MeiziContact {

    interface view extends BaseContact.baseView {
        LybRecycleView getRecycleView();
        MeiziRecyclerAdapter getAdapter();
    }

    interface model {
        Observable<BaseResult<List<String>>> getMeiziList(int index);
    }

    interface presenter extends BaseContact.basePresenter{
        void getMeiziData(int pageIndex);
    }
}
