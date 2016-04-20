package com.android.framework.demo.activity.themechange;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
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

    private CheckBox checkBox;
    private Toolbar toolbar;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_theme_change;
    }

    @Override
    public void findViews() {

        checkBox = f(R.id.checkbox_hobby);
        toolbar = f(R.id.toolbar);

    }

    @Override
    public void setListeners() {
        checkBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) ->
                PreferenceUtil.getInstance(this).saveParam(TAG + "isChecked", isChecked));

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

        toolbar.setTitle("中间Activity");

        checkBox.setChecked(PreferenceUtil.getInstance(this).getBooleanParam(TAG + "isChecked", true));

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
            case R.id.btn_start_next_activity:
                ThemeChange2Activity.start(this);
                return;
            default:
                break;
        }

        PreferenceUtil.getInstance(this).saveParam(ThemeUtils.CURRENT_THEME_KEY, themeValue);

        // 通知 其它 已经加载的Activity更新主题
        ThemeUtils.sendThemeChangeBroadCast(this);

    }

}
