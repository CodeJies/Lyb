package com.codejies.lybwidget.widget.lybrecyclerview;

//用于记录头部在拖动时状态等变化的接口
public interface BaseRefreshHeader {

	int STATE_NORMAL = 0;//常態
	int STATE_RELEASE_TO_REFRESH = 1;//松开即刷新
	int STATE_REFRESHING = 2;//刷新中
	int STATE_DONE = 3;//刷新结束

	void onMove(float delta);//滑动操作进行中

	boolean releaseAction();//刷新动作进行中

	void refreshComplete();//刷新动作完成时

}