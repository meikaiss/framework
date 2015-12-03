package com.android.framework.base;

/**
 * Created by meikai on 15/12/3.
 */
public interface BaseAction {

    int getContentViewLayoutId();

    void findViews();

    void setListeners();

    void parseBundle();

    void afterView();

}
