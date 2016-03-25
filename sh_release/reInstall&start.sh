#!/usr/bin/env bash
adb install -r ../_apk/release/app-release-V1.2-#0.apk
adb shell am start -n com.android.framework.demo/com.android.framework.demo.MainActivity