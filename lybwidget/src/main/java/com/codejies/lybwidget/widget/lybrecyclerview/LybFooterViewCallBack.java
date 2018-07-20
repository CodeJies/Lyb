package com.codejies.lybwidget.widget.lybrecyclerview;

import android.view.View;

/**
 * Created by Jies on 2018/7/17.
 */

public interface LybFooterViewCallBack {
    public void onLoadingMore(View footerView);
    void onLoadingMoreComplete(View footerView);
    void onSetNoMore(View footerView,boolean noMore);
}
