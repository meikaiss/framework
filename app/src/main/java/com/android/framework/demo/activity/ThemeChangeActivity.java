package com.android.framework.demo.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.android.framework.util.PreferenceUtil;
import com.android.framework.utils.ThemeUtils;

/**
 * Created by meikai on 16/4/20.
 */
public class ThemeChangeActivity extends BaseCompactActivity implements View.OnClickListener {

    private static final String TAG = "ThemeChangeActivity";

    CheckBox checkBox;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_theme_change;
    }

    @Override
    public void findViews() {

        checkBox = (CheckBox) findViewById(R.id.checkbox_hobby);

    }

    @Override
    public void setListeners() {
        checkBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) ->
        {
            PreferenceUtil.getInstance(this, TAG).saveParam("isChecked", isChecked);
        });

    }

    @Override
    public void parseBundle() {

    }

    @Override
    public void afterView() {
        checkBox.setChecked(PreferenceUtil.getInstance(this, TAG).getBooleanParam("isChecked", true));
    }

    @Override
    public void onClick(View v) {

        int themeValue = 0;

        switch (v.getId()) {
            case R.id.btn_red:
                themeValue = 0;
                break;
            case R.id.btn_brown:
                themeValue = 1;
                break;
            case R.id.btn_blue:
                themeValue = 2;
                break;
            case R.id.btn_blue_grey:
                themeValue = 3;
                break;
            case R.id.btn_yellow:
                themeValue = 4;
                break;
            case R.id.btn_deep_purple:
                themeValue = 5;
                break;
            case R.id.btn_pink:
                themeValue = 6;
                break;
            case R.id.btn_green:
                themeValue = 7;
                break;
            default:
                break;
        }

        PreferenceUtil.getInstance(this, ThemeUtils.PREFERENCE_FILE_NAME).saveParam(ThemeUtils.CURRENT_THEME_KEY, themeValue);
        this.reload();


        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent();
        intent.setAction(ThemeUtils.BROADCAST_ACTION_THEME_CHANGE);
        localBroadcastManager.sendBroadcast(intent);

    }
}
