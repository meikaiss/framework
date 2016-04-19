package com.android.framework.base;

import android.support.annotation.LayoutRes;

/**
 * Created by meikai on 15/12/3.
 */
public interface BaseAction {

    @LayoutRes int getContentViewLayoutId();

    void findViews();

    void setListeners();

    void parseBundle();

    void afterView();

}
