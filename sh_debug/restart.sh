#!/usr/bin/env bash
adb shell am force-stop  com.android.framework.demo
adb shell am start -n com.android.framework.demo/com.android.framework.demo.MainActivity