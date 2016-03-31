//
// Created by meikai on 16/3/31.
//


#include "NdkTest.h"

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_getStringInNDK
        (JNIEnv *env, jclass jc)
{
    return env->NewStringUTF("Hello NDK !!!\n this is a string in so");
}