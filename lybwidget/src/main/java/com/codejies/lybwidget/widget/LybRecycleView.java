package com.codejies.lybwidget.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.codejies.lybwidget.widget.lybrecyclerview.LybFooterViewCallBack;
import com.codejies.lybwidget.widget.lybrecyclerview.LybLoadingListener;
import com.codejies.lybwidget.widget.lybrecyclerview.LybLoadingMoreFooter;
import com.codejies.lybwidget.widget.lybrecyclerview.LybRefreshHeadView;

import java.util.ArrayList;
import java.util.List;

import static com.codejies.lybwidget.widget.lybrecyclerview.BaseRefreshHeader.STATE_DONE;

/**
 * 适用于Lyb项目中的RecycleView
 * Created by Jies on 2018/7/17.
 */

public class LybRecycleView extends RecyclerView {
    private boolean loadingMoreEnabled = true;//加载更多能否使用
    private boolean pullRefreshEnabled = true;//是否能夠使用刷新操作
    private boolean isLoadingData = false;//是否在加載數據的標識
    private boolean isNoMore = false;//是否沒有更多數據的標識
    //下面的ItemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。不过为了简化,我们检测到重复时对用户的提示是ItemViewType必须小于10000
    private static final int TYPE_REFRESH_HEADER = 10000;//设置一个很大的数字,尽可能避免和用户的adapter冲突
    private static final int TYPE_FOOTER = 10001;
    private static final int HEADER_INIT_INDEX = 10002;
    private static List<Integer> sHeaderTypes = new ArrayList<>();//每个header必须有不同的type,不然滚动的时候顺序会变化
    private ArrayList<View> mHeaderViews = new ArrayList<>();//头部View的集合
    private WrapAdapter mWrapAdapter;
    private float mLastY = -1;//最后的Y坐标位置
    private static final float DRAG_RATE = 3;//拖动时的摩擦系数
    private LybFooterViewCallBack lybFooterViewCallBack;//滑动到底部时监听事件
    private LybLoadingListener lybLoadingListener;//加載更多跟刷新的监听


    private LybRefreshHeadView mRefreshHeader;

    private View mFootView;
    private View mEmptyView;

    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();

    int limitNumberToCallLoadMore = 1;//当剩余这个数量时启动加载更多操作


    private int scrollDyCounter = 0;

    public LybRecycleView(Context context) {
        this(context, null);
    }

    public LybRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LybRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //初始化方法
    private void init() {
        if (pullRefreshEnabled) {
            mRefreshHeader = new LybRefreshHeadView(getContext());
        }
        LybLoadingMoreFooter footView = new LybLoadingMoreFooter(getContext());
        mFootView = footView;
        mFootView.setVisibility(GONE);
    }

    public void destroy() {
        if (mHeaderViews != null) {
            mHeaderViews.clear();
            mHeaderViews = null;
        }
        if (mFootView instanceof LybLoadingMoreFooter) {
            ((LybLoadingMoreFooter) mFootView).destroy();
            mFootView = null;
        }
        if (mRefreshHeader != null) {
            mRefreshHeader.destroy();
            mRefreshHeader = null;
        }
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        if (mFootView instanceof LybLoadingMoreFooter) {
            ((LybLoadingMoreFooter) mFootView).setState(LybLoadingMoreFooter.STATE_COMPLETE);
        } else {
            if (lybFooterViewCallBack != null) {
                lybFooterViewCallBack.onLoadingMoreComplete(mFootView);
            }
        }
    }


    public void setNoMore(boolean noMore) {
        isLoadingData = false;
        isNoMore = noMore;
        if (mFootView instanceof LybLoadingMoreFooter) {
            ((LybLoadingMoreFooter) mFootView).setState(isNoMore ? LybLoadingMoreFooter.STATE_NOMORE : LybLoadingMoreFooter.STATE_COMPLETE);
        } else {
            if (lybFooterViewCallBack != null) {
                lybFooterViewCallBack.onSetNoMore(mFootView, noMore);
            }
        }
    }

    //刷新方法
    public void refresh() {
        if (pullRefreshEnabled && lybLoadingListener != null) {
            mRefreshHeader.setState(LybRefreshHeadView.STATE_REFRESHING);
            lybLoadingListener.onRefresh();
        }
    }

    public void reset() {
        setNoMore(false);
        loadMoreComplete();
        refreshComplete();
    }

    public void refreshComplete() {
        if (mRefreshHeader != null)
            mRefreshHeader.refreshComplete();
        setNoMore(false);
    }

    public void addHeaderView(View view) {
        if (mHeaderViews == null || sHeaderTypes == null)
            return;
        sHeaderTypes.add(HEADER_INIT_INDEX + mHeaderViews.size());
        mHeaderViews.add(view);
        if (mWrapAdapter != null) {
            mWrapAdapter.notifyDataSetChanged();
        }
    }


    //判断一个type是否为HeaderType
    private boolean isHeaderType(int itemViewType) {
        if (mHeaderViews == null || sHeaderTypes == null)
            return false;
        return mHeaderViews.size() > 0 && sHeaderTypes.contains(itemViewType);
    }

    //根据header的ViewType判断是哪个header
    private View getHeaderViewByType(int itemType) {
        if (!isHeaderType(itemType)) {
            return null;
        }
        if (mHeaderViews == null)
            return null;
        return mHeaderViews.get(itemType - HEADER_INIT_INDEX);
    }


    //判断是否是LybRecyclerView保留的itemViewType
    private boolean isReservedItemViewType(int itemViewType) {
        if (itemViewType == TYPE_REFRESH_HEADER || itemViewType == TYPE_FOOTER || sHeaderTypes.contains(itemViewType)) {
            return true;
        } else {
            return false;
        }
    }

    public void setLybLoadingListener(LybLoadingListener listener) {
        this.lybLoadingListener = listener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    //避免用户自己调用getAdapter() 引起的ClassCastException
    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null)
            return mWrapAdapter.getOriginalAdapter();
        else
            return null;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (mWrapAdapter != null) {
            if (layout instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) layout);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (mWrapAdapter.isHeader(position) || mWrapAdapter.isFooter(position) || mWrapAdapter.isRefreshHeader(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }
    }

    public <T> void notifyItemRemoved(List<T> listData, int position) {
        if (mWrapAdapter.adapter == null)
            return;
        int headerSize = getHeaders_includingRefreshCount();
        int adjPos = position + headerSize;
        mWrapAdapter.adapter.notifyItemRemoved(adjPos);
        mWrapAdapter.adapter.notifyItemRangeChanged(headerSize, listData.size(), new Object());
    }

    public <T> void notifyItemInserted(List<T> listData, int position) {
        if (mWrapAdapter.adapter == null)
            return;
        int headerSize = getHeaders_includingRefreshCount();
        int adjPos = position + headerSize;
        mWrapAdapter.adapter.notifyItemInserted(adjPos);
        mWrapAdapter.adapter.notifyItemRangeChanged(headerSize, listData.size(), new Object());
    }

    public void notifyItemChanged(int position) {
        if (mWrapAdapter.adapter == null)
            return;
        int adjPos = position + getHeaders_includingRefreshCount();
        mWrapAdapter.adapter.notifyItemChanged(adjPos);
    }

    public void notifyItemChanged(int position, Object o) {
        if (mWrapAdapter.adapter == null)
            return;
        int adjPos = position + getHeaders_includingRefreshCount();
        mWrapAdapter.adapter.notifyItemChanged(adjPos, o);
    }

    private int getHeaders_includingRefreshCount() {
        return mWrapAdapter.getHeadersCount() + 1;
    }

    //滑动时状态触发回调
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && lybLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            int adjAdapterItemCount = layoutManager.getItemCount() + getHeaders_includingRefreshCount();
            Log.e("aaaaa", "adjAdapterItemCount " + adjAdapterItemCount + " getItemCount " + layoutManager.getItemCount());

            int status = STATE_DONE;

            if (mRefreshHeader != null)
                status = mRefreshHeader.getState();
            if (
                    layoutManager.getChildCount() > 0
                            && lastVisibleItemPosition >= adjAdapterItemCount - limitNumberToCallLoadMore
                            && adjAdapterItemCount >= layoutManager.getChildCount()
                            && !isNoMore
                            && status < LybRefreshHeadView.STATE_REFRESHING
                    ) {
                isLoadingData = true;
                if (mFootView instanceof LybLoadingMoreFooter) {
                    ((LybLoadingMoreFooter) mFootView).setState(LybLoadingMoreFooter.STATE_LOADING);
                } else {
                    if (lybFooterViewCallBack != null) {
                        lybFooterViewCallBack.onLoadingMore(mFootView);
                    }
                }
                lybLoadingListener.onLoadMore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isOnTop() && pullRefreshEnabled) {
                    if (mRefreshHeader == null)
                        break;
                    mRefreshHeader.onMove(deltaY / DRAG_RATE);
                    if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() < LybRefreshHeadView.STATE_REFRESHING) {
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled) {
                    if (mRefreshHeader != null && mRefreshHeader.releaseAction()) {
                        if (lybLoadingListener != null) {
                            lybLoadingListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private boolean isOnTop() {
        if (mRefreshHeader == null)
            return false;
        if (mRefreshHeader.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (scrollAlphaChangeListener == null) {
            return;
        }
        int height = scrollAlphaChangeListener.setLimitHeight();
        scrollDyCounter = scrollDyCounter + dy;
        if (scrollDyCounter <= 0) {
            scrollAlphaChangeListener.onAlphaChange(0);
        } else if (scrollDyCounter <= height && scrollDyCounter > 0) {
            float scale = (float) scrollDyCounter / height; /** 255/height = x/255 */
            float alpha = (255 * scale);
            scrollAlphaChangeListener.onAlphaChange((int) alpha);
        } else {
            scrollAlphaChangeListener.onAlphaChange(255);
        }
    }

    private ScrollAlphaChangeListener scrollAlphaChangeListener;

    public void setScrollAlphaChangeListener(
            ScrollAlphaChangeListener scrollAlphaChangeListener
    ) {
        this.scrollAlphaChangeListener = scrollAlphaChangeListener;
    }

    public interface ScrollAlphaChangeListener {
        void onAlphaChange(int alpha);

        /**
         * you can handle the alpha insert it
         */
        int setLimitHeight(); /** set a height for the begging of the alpha start to change */
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
            if (mWrapAdapter != null && mEmptyView != null) {
                int emptyCount = 1 + mWrapAdapter.getHeadersCount();
                if (loadingMoreEnabled) {
                    emptyCount++;
                }
                if (mWrapAdapter.getItemCount() == emptyCount) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    LybRecycleView.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    LybRecycleView.this.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }


    private class WrapAdapter extends RecyclerView.Adapter<ViewHolder> {

        private RecyclerView.Adapter adapter;

        public WrapAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        public RecyclerView.Adapter getOriginalAdapter() {
            return this.adapter;
        }

        public boolean isHeader(int position) {
            if (mHeaderViews == null)
                return false;
            return position >= 1 && position < mHeaderViews.size() + 1;
        }

        public boolean isFooter(int position) {
            if (loadingMoreEnabled) {
                return position == getItemCount() - 1;
            } else {
                return false;
            }
        }

        public boolean isRefreshHeader(int position) {
            return position == 0;
        }

        public int getHeadersCount() {
            if (mHeaderViews == null)
                return 0;
            return mHeaderViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new SimpleViewHolder(mRefreshHeader == null ? new LybRefreshHeadView(getContext()) : mRefreshHeader);
            } else if (isHeaderType(viewType)) {
                return new SimpleViewHolder(getHeaderViewByType(viewType));
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootView);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return;
            }
            int adjPosition = position - (getHeadersCount() + 1);
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        // some times we need to override this
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return;
            }

            int adjPosition = position - (getHeadersCount() + 1);
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    if (payloads.isEmpty()) {
                        adapter.onBindViewHolder(holder, adjPosition);
                    } else {
                        adapter.onBindViewHolder(holder, adjPosition, payloads);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            int adjLen = (loadingMoreEnabled ? 2 : 1);
            if (adapter != null) {
                return getHeadersCount() + adapter.getItemCount() + adjLen;
            } else {
                return getHeadersCount() + adjLen;
            }
        }

        @Override
        public int getItemViewType(int position) {
            int adjPosition = position - (getHeadersCount() + 1);
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)) {
                position = position - 1;
                return sHeaderTypes.get(position);
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    int type = adapter.getItemViewType(adjPosition);
                    if (isReservedItemViewType(type)) {
                        throw new IllegalStateException("XRecyclerView require itemViewType in adapter should be less than 10000 ");
                    }
                    return type;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount() + 1) {
                int adjPosition = position - (getHeadersCount() + 1);
                if (adjPosition < adapter.getItemCount()) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position) || isRefreshHeader(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
            adapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) || isRefreshHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            adapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
            return adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            adapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            adapter.registerAdapterDataObserver(observer);
        }

        private class SimpleViewHolder extends RecyclerView.ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
