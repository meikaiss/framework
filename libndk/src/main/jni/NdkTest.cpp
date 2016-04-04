//
// Created by meikai on 16/3/31.
//

#include "NdkTest.h"
#include "encrypt.h"

jstring CharTojstring(JNIEnv *env, char *str);

char *jstringToChar(JNIEnv *env, jstring jstr);

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_getStringInNDK
        (JNIEnv *env, jclass jc) {

    return env->NewStringUTF("Hello NDK !!!\n this is a string in so");
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_converseInSo
        (JNIEnv *env, jobject obj, jstring source) {

    const char *pSourceChar;
    pSourceChar = env->GetStringUTFChars(source, (jboolean *) false);

    if (pSourceChar == NULL) {
        return NULL; /* OutOfMemoryError already thrown */
    }

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
        (JNIEnv *env, jclass jc, jbyteArray src) {


    jstring sourceString = env->NewStringUTF("Hello NDK !!!\n this is a string in so");

    int len = env->GetStringLength(sourceString);
    char *c1 = jstringToChar(env, sourceString);

    char *encryptChar = encrypt(c1, len);
    jstring encryptResult = CharTojstring(env, encryptChar);


    char *decryptChar = decrypt(encryptChar, len);
    jstring decryptResult = CharTojstring(env, decryptChar);


    return encryptResult;
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_decryptString
        (JNIEnv *env, jclass jc, jbyteArray src) {

    jsize len0 = env->GetArrayLength(src);
    jbyte *arrayBody = (jbyte *) malloc(len0 * sizeof(jbyte));
    env->GetByteArrayRegion(src, 0, len0, arrayBody);


    jstring sourceString = CharTojstring(env, (char *) arrayBody);

    int len = env->GetStringLength(sourceString);
    char *c1 = jstringToChar(env, sourceString);

    char *encryptChar = encrypt(c1, len);
    jstring encryptResult = CharTojstring(env, encryptChar);


    char *decryptChar = decrypt(encryptChar, len);
    jstring decryptResult = CharTojstring(env, decryptChar);

    free(arrayBody);

    return decryptResult;
}
