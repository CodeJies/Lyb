package com.codejies.lyb.page.home.meizi;

import com.codejies.lyb.bean.BaseResult;
import com.codejies.lyb.network.LybApiManager;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Jies on 2018/8/14.
 */

public class MeiziModel implements MeiziContact.model {
    @Override
    public Observable<BaseResult<List<String>>> getMeiziList(int index) {
        return LybApiManager.getApiService().getMeiziList(index);
    }
}
