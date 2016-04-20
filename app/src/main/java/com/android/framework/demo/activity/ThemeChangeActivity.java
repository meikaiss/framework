package com.android.framework.demo.activity;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;
import com.android.framework.util.PermissionUtil;
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
        switch (v.getId()) {
            case R.id.btn_red:
                PreferenceUtil.getInstance(this, ThemeUtils.PREFERENCE_FILE_NAME).saveParam(ThemeUtils.CURRENT_THEME_KEY, 0);
                break;
            case R.id.btn_brown:
                PreferenceUtil.getInstance(this, ThemeUtils.PREFERENCE_FILE_NAME).saveParam(ThemeUtils.CURRENT_THEME_KEY, 1);

                break;
            case R.id.btn_blue:
                PreferenceUtil.getInstance(this, ThemeUtils.PREFERENCE_FILE_NAME).saveParam(ThemeUtils.CURRENT_THEME_KEY, 2);

                break;
            default:
                break;
        }
        this.reload();


        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent();
        intent.setAction(ThemeUtils.BROADCAST_ACTION_THEME_CHANGE);
        localBroadcastManager.sendBroadcast(intent);

    }
}
