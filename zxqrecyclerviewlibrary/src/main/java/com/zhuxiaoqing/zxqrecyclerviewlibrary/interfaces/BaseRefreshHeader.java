package com.zhuxiaoqing.zxqrecyclerviewlibrary.interfaces;

/**
 * Created by zhuxiaoqing on 2017/9/28.
 * good luck~ (@^_^@)
 * 刷新接口回调函数
 */

public interface BaseRefreshHeader {
    int STATE_NORMAL = 0;//没有更多了
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();
}
