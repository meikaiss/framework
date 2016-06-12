package com.android.framework.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.framework.base.BaseCompactActivity;
import com.android.framework.demo.R;

/**
 * Created by meikai on 16/5/30.
 */
public class ScreenLightSettingActivity extends BaseCompactActivity {


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_screen_light_setting;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void setListeners() {

        findViewById(R.id.btn_light_mode).setOnClickListener(v1 -> {

            try {
                //  获取系统亮度模式
                int lightMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
                if (lightMode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
                    Toast.makeText(ScreenLightSettingActivity.this, "当前模式:手动设置亮度", Toast.LENGTH_SHORT).show();
                } else if (lightMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                    Toast.makeText(ScreenLightSettingActivity.this, "当前模式:自动设置亮度", Toast.LENGTH_SHORT).show();
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(v -> {

//            changeAppBrightness(this, 80);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);

            // 获取系统亮度
//            try {
//                int light = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
//                Toast.makeText(ScreenLightSettingActivity.this, "" + light, Toast.LENGTH_SHORT).show();
//            } catch (Settings.SettingNotFoundException e) {
//                e.printStackTrace();
//            }
        });


        findViewById(R.id.btn_dec).setOnClickListener(v -> {
//            changeAppBrightness(this, 40);

            // 如果 当前是 自动 模式, 则手动设置 是没有效果的
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 50);


        });

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }

    // 根据亮度值修改当前window亮度
    public void changeAppBrightness(Context context, int brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 100f;
        }
        window.setAttributes(lp);
    }
}
