LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := libndk
LOCAL_SRC_FILES := NdkTest.c
include $(BUILD_SHARED_LIBRARY)
