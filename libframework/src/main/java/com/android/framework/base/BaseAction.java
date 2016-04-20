package com.android.framework.base;

import android.content.Intent;
import android.support.annotation.LayoutRes;

/**
 * Created by meikai on 15/12/3.
 */
public interface BaseAction {

    @LayoutRes int getContentViewLayoutId();

    void findViews();

    void setListeners();

    void parseBundle(Intent intent);

    void afterView();



    void onThemeChange();

}
