#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_android_framework_ndk_NdkTest_getStringInNDK(JNIEnv *env, jclass type) {

    // TODO


    return (*env)->NewStringUTF(env, "Hello NDK!!!\n this is a string in so");
}

