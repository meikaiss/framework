package com.android.framework.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.framework.R;
import com.android.framework.theme.ThemeManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by meikai on 15/10/29.
 */
public abstract class BaseCompactActivity extends AppCompatActivity implements BaseAction {

    public final static String IS_START_ANIM = "IS_START_ANIM";

    private ThemeChangeBroadCastReceiver broadCastReceiver;

    public boolean isDestroyed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initTheme();

        super.onCreate(savedInstanceState);

        initSystemBarTint(true);

        setContentView(getContentViewLayoutId());

        registerThemeChangeBroadCast();

        findViews();
        setListeners();
        parseBundle(getIntent());
        afterView();
    }

    @Override
    public void onThemeChange() {
        reload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(broadCastReceiver);
    }

    private void initTheme() {
        ThemeManager.Theme theme = ThemeManager.getCurrentTheme(this);
        ThemeManager.changeTheme(this, theme);
    }

    public void initSystemBarTint(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatusNavigation(on);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(on);
            tintManager.setStatusBarTintColor(getColorPrimary());
            tintManager.setNavigationBarTintEnabled(on);
            tintManager.setNavigationBarTintColor(getColorPrimary());
        }
    }

    private void registerThemeChangeBroadCast() {
        broadCastReceiver = new ThemeChangeBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ThemeManager.BROADCAST_ACTION_THEME_CHANGE);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(broadCastReceiver, intentFilter);
    }

    public final void reload() {
        reload(false);
    }

    public final void reload(boolean anim) {
        Intent intent = getIntent();
        if (!anim) {
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra(BaseCompactActivity.IS_START_ANIM, false);
        }
        finish();
        if (!anim) {
            overridePendingTransition(0, 0);
        }
        startActivity(intent);
    }

    private void setTranslucentStatusNavigation(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();

        int bitS = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        int bitN = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bitS;
            winParams.flags |= bitN;
        } else {
            winParams.flags &= ~bitS;
            winParams.flags &= ~bitN;
        }
        window.setAttributes(winParams);
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }


    public <T extends View> T f(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    public <T extends View> T f(View view, @IdRes int viewId) {
        return (T) view.findViewById(viewId);
    }


    class ThemeChangeBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ThemeManager.BROADCAST_ACTION_THEME_CHANGE)) {
                onThemeChange();
            }
        }
    }

}
