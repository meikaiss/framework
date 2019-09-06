package com.android.framework.demo.activity;

import android.content.Intent;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.android.framework.util.LogUtil;

import java.util.Calendar;

/**
 * 研究 Include 属性
 * Created by meikai on 2019/08/13.
 */
public class IncludeActivity extends BaseCompactActivity {

    private static final String TAG = "IncludeActivity";

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_include;
    }

    @Override
    public void findViews() {


    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

        Calendar remindCalendar = Calendar.getInstance();

        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_WEEK) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_MONTH) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_YEAR) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.MONTH) + "");
        LogUtil.e(TAG, remindCalendar.getTime().toString());

        LogUtil.e(TAG, "--------");

        remindCalendar.set(Calendar.DAY_OF_WEEK, 3);
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_WEEK) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_MONTH) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.DAY_OF_YEAR) + "");
        LogUtil.e(TAG, remindCalendar.get(Calendar.MONTH) + "");
        LogUtil.e(TAG, remindCalendar.getTime().toString());

    }
}
