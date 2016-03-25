#!/usr/bin/env bash

#adb install -r ../app/build/outputs/apk/app-debug-1.2-#0.apk

adb install -r ../_apk/debug/app-debug-V1.2-#0.apk
adb shell am start -n com.android.framework.demo/com.android.framework.demo.MainActivity