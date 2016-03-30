//
// Created by meikai on 16/3/31.
//


#include "com_android_framework_ndk_NdkTest.h"

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_getStringInNDK
        (JNIEnv *env, jclass jc)
{
    return env->NewStringUTF("Hello NDK !!!\n this is a string in so");
}