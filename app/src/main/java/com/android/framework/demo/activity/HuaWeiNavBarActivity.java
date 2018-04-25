package com.android.framework.demo.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.android.framework.demo.R;

/**
 * Created by meikai on 2018/03/27.
 */
public class HuaWeiNavBarActivity extends AppCompatActivity {

    int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hua_web_nav_bar_activity);

        findViewById(R.id.btn_2_lan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    toFullScreen(getWindow());
                } else {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    exitFullScreen(getWindow());
                }

                HuaWeiNavBarActivity.this.setRequestedOrientation(orientation);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    /**
     * 动态全屏
     */
    public void toFullScreen(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setAttributes(params);
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //去掉虚拟按键全屏显示

        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);

//                findViewById(R.id.tv_word).setPadding(0, 0, 0, 0);

//                findViewById(R.id.tv_word).requestLayout();
            }
        }, 2000);

//        window.getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_IMMERSIVE);
        //window.getDecorView().requestLayout();
    }

    /**
     * 动态退出全屏
     */
    public static void exitFullScreen(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setAttributes(params);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        int newFlag = window.getDecorView().getSystemUiVisibility();
        newFlag &= (~View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        newFlag &= (~View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        newFlag &= (~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        newFlag &= (~View.SYSTEM_UI_FLAG_IMMERSIVE);

        window.getDecorView().setSystemUiVisibility(newFlag);

    }


}
