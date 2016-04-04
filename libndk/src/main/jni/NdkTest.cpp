//
// Created by meikai on 16/3/31.
//

#include "NdkTest.h"
#include "encrypt.h"


JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_getStringInNDK
        (JNIEnv *env, jclass jc) {

    return env->NewStringUTF("Hello NDK !!!\n this is a string in so");
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_converseInSo
        (JNIEnv *env, jobject obj, jstring source) {

    const char *pSourceChar;
    pSourceChar = env->GetStringUTFChars(source, (jboolean *) false);

    int len = strlen(pSourceChar);
    char *converseChar = new char[len];
    for (int i = 0; i < len; i++) {
        converseChar[i] = pSourceChar[len - 1 - i];
    }
    converseChar[len] = '\0';

    jstring converseJString = env->NewStringUTF(converseChar);

    env->ReleaseStringUTFChars(source, pSourceChar);

    return converseJString;
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_encryptString
        (JNIEnv *env, jobject obj, jstring source) {

    const char *pSourceChar = env->GetStringUTFChars(source, (jboolean *) false);
    int len = strlen(pSourceChar);

    char *encryptChar = encrypt((char *) pSourceChar, len);
    jstring encryptResult = env->NewStringUTF(encryptChar);

    env->ReleaseStringUTFChars(source, pSourceChar);

    return encryptResult;
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_decryptString
        (JNIEnv *env, jobject obj, jstring source) {

    const char *pSourceChar = env->GetStringUTFChars(source, (jboolean *) false);
    int len = strlen(pSourceChar);

    char *encryptChar = decrypt((char *) pSourceChar, len);
    jstring encryptResult = env->NewStringUTF(encryptChar);

    env->ReleaseStringUTFChars(source, pSourceChar);

    return encryptResult;
}
