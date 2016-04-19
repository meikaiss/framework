package com.android.framework.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.framework.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by meikai on 15/10/29.
 */
public abstract class BaseCompactActivity extends AppCompatActivity implements BaseAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSystemBarTint(true);

        setContentView(getContentViewLayoutId());

        findViews();
        setListeners();
        parseBundle();
        afterView();
    }

    public <T extends View> T f(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    public <T extends View> T f(View view, @IdRes int viewId) {
        return (T) view.findViewById(viewId);
    }

    public void initSystemBarTint(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(on);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(on);
            tintManager.setStatusBarTintColor(getColorPrimary());
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();

        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
