#!/usr/bin/env bash
adb install -r ../app/build/outputs/apk/app-release-1.2-#0.apk
adb shell am start -n com.android.framework.demo/com.android.framework.demo.MainActivity