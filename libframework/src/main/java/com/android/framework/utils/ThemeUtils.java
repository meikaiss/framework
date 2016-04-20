package com.android.framework.utils;

import android.app.Activity;
import android.content.Context;

import com.android.framework.R;
import com.android.framework.util.PreferenceUtil;

/**
 * Created by meikai on 16/4/20.
 */
public class ThemeUtils {

    public static final String PREFERENCE_FILE_NAME = "AppThemePreferenceFile";
    public static final String CURRENT_THEME_KEY = "current_theme_key";
    public static final String BROADCAST_ACTION_THEME_CHANGE = "broadcast_action_theme_change";

    public static void changeTheme(Activity activity, Theme theme) {
        if (activity == null)
            return;
        int style = R.style.RedTheme;
        switch (theme) {
            case BROWN:
                style = R.style.BrownTheme;
                break;
            case BLUE:
                style = R.style.BlueTheme;
                break;
            case BLUE_GREY:
                style = R.style.BlueGreyTheme;
                break;
            case YELLOW:
                style = R.style.YellowTheme;
                break;
            case DEEP_PURPLE:
                style = R.style.DeepPurpleTheme;
                break;
            case PINK:
                style = R.style.PinkTheme;
                break;
            case GREEN:
                style = R.style.GreenTheme;
                break;
            default:
                break;
        }
        activity.setTheme(style);
    }

    public static Theme getCurrentTheme(Context context) {
        int value = PreferenceUtil.getInstance(context, PREFERENCE_FILE_NAME)
                .getIntParam(CURRENT_THEME_KEY, 0);
        return ThemeUtils.Theme.mapValueToTheme(value);
    }

    public enum Theme {
        RED(0x00),
        BROWN(0x01),
        BLUE(0x02),
        BLUE_GREY(0x03),
        YELLOW(0x04),
        DEEP_PURPLE(0x05),
        PINK(0x06),
        GREEN(0x07);

        private int mValue;

        Theme(int value) {
            this.mValue = value;
        }

        public static Theme mapValueToTheme(final int value) {
            for (Theme theme : Theme.values()) {
                if (value == theme.getIntValue()) {
                    return theme;
                }
            }
            // If run here, return default
            return RED;
        }

        static Theme getDefault() {
            return RED;
        }

        public int getIntValue() {
            return mValue;
        }
    }
}
