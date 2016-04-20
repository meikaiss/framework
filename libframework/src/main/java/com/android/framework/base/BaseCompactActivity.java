package com.android.framework.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.framework.R;
import com.android.framework.utils.ThemeUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by meikai on 15/10/29.
 */
public abstract class BaseCompactActivity extends AppCompatActivity implements BaseAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initTheme();

        super.onCreate(savedInstanceState);

        initSystemBarTint(true);

        setContentView(getContentViewLayoutId());

        findViews();
        setListeners();
        parseBundle();
        afterView();
    }

    private void initTheme() {
        ThemeUtils.Theme theme = ThemeUtils.getCurrentTheme(this);
        ThemeUtils.changeTheme(this, theme);
    }

    public void reload() {
        reload(false);
    }

    public void reload(boolean anim) {
        Intent intent = getIntent();
        if (!anim) {
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            intent.putExtra(BaseActivity.IS_START_ANIM, false);
        }
        finish();
        if (!anim) {
            overridePendingTransition(0, 0);
        }
        startActivity(intent);
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

    private void setTranslucentStatusNavigation(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();

        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        int bitn = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
            winParams.flags |= bitn;
        } else {
            winParams.flags &= ~bits;
            winParams.flags &= ~bitn;
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
}
