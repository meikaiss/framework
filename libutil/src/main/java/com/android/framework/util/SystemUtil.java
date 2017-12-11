package com.android.framework.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by meikai on 2017/12/08.
 */
public class SystemUtil {


    /**
     * 设置 status bar 亮暗
     *
     * @param isDark 亮还是暗
     * @param activity 当前activity
     */
    public static void setStatusBarDarkMode(boolean isDark, Activity activity) {
        String miuiVersion = SystemUtil.getMIUIVersion();
        if (StringUtil.isNotEmpty(miuiVersion)) {
            SystemUtil.setAllMIUIStatusBarDarkMode(isDark, miuiVersion, activity);
        } else {
            googleStatusBarDarkMode(isDark, activity);
        }
    }

    /**
     * MIUI处理状态栏，要分版本，单独拿出来
     *
     * @param isDark mode是亮还是暗
     * @param miuiVersion 版本
     */
    public static void setAllMIUIStatusBarDarkMode(boolean isDark, String miuiVersion, Activity activity) {
        if ("V6".equals(miuiVersion) || "V7".equals(miuiVersion) || "V8".equals(miuiVersion)) {
            SystemUtil.setMIUIStatusBarDarkMode(isDark, activity);
        } else if ("V9".equals(miuiVersion)) {
            SystemUtil.setMIUI9StatusBarDarkMode(isDark, activity);
        }
    }

    /**
     * MIUI9以下，或者MIUI9 sdk23以下使用小米自己的方法改status bar
     */
    public static void setMIUIStatusBarDarkMode(boolean isFontDark, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), isFontDark ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
        }
    }

    /**
     * MIUI9并且23以上回归谷歌原生修改方法
     * 其余MIUI用小米自己的方法修改
     */
    public static void setMIUI9StatusBarDarkMode(boolean isFontDark, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            if (isFontDark) {
                decor.setSystemUiVisibility(decor.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(decor.getSystemUiVisibility() | ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            setMIUIStatusBarDarkMode(isFontDark, activity);
        }
    }

    public static String getMIUIVersion() {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException e) {

        } finally {

        }
        return line;
    }

    /**
     * 谷歌官方大于23的处理状态栏方法
     *
     * @param isDark mode是亮还是暗
     * @param activity 当前activity
     */
    private static void googleStatusBarDarkMode(boolean isDark, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            if (isDark) {
                decor.setSystemUiVisibility(decor.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(decor.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

}
