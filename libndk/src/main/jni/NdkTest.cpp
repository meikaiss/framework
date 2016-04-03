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

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_encryptString
        (JNIEnv *env, jclass jc, jbyteArray src) {


    jstring sourceString = env->NewStringUTF("Hello NDK !!!\n this is a string in so");

    int len = env->GetStringLength(sourceString);
    char *c1 = jstringToChar(env, sourceString);

    char *encryptChar = encrypt(c1, len);
    jstring encryptResult = CharTojstring(env, encryptChar);


    char * decryptChar = decrypt(encryptChar, len);
    jstring decryptResult = CharTojstring(env, decryptChar);



    return encryptResult;
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_decryptString
        (JNIEnv *env, jclass jc, jbyteArray src) {

    jsize len0 = env->GetArrayLength(src);
    jbyte* arrayBody = (jbyte *)malloc(len0 * sizeof(jbyte));
    env->GetByteArrayRegion(src,0,len0,arrayBody);


    jstring sourceString = CharTojstring(env, (char *)arrayBody);

    int len = env->GetStringLength(sourceString);
    char *c1 = jstringToChar(env, sourceString);

    char *encryptChar = encrypt(c1, len);
    jstring encryptResult = CharTojstring(env, encryptChar);


    char * decryptChar = decrypt(encryptChar, len);
    jstring decryptResult = CharTojstring(env, decryptChar);

    free(arrayBody);

    return decryptResult;
}

//////
jstring CharTojstring(JNIEnv *env, char *str) {
    jstring rtn = 0;
    jsize len;
    jclass clsstring;
    jstring strencode;
    jmethodID mid;
    jbyteArray barr;

    if (0 == str) {
        // jstring对象为空是返回0，不能返回""
        // 不要想当然的把jstring等同为C++的string
        return (jstring) 0;
    }

    len = (int) strlen(str);

    if (0 == len) {
        // jstring对象为空是返回0，不能返回""
        // 不要想当然的把jstring等同为C++的string
        return (jstring) 0;
    }

    clsstring = env->FindClass("java/lang/String");

    //new   encode   string   default   "UTF-8 "
    strencode = env->NewStringUTF("UTF-8");
    mid = env->GetMethodID(clsstring, "<init>", "([BLjava/lang/String;)V");
    barr = env->NewByteArray(len);

    env->SetByteArrayRegion(barr, 0, len, (jbyte *) str);

    rtn = (jstring) env->NewObject(clsstring, mid, barr, strencode);

    return rtn;
}

char *jstringToChar(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");

    //new   encode   string   default   "UTF-8 "
    jstring strencode = env->NewStringUTF("UTF-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");

    //call   String.getBytes   method   to   avoid   incompatible   migrating   into   solaris
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);

    jsize alen = env->GetArrayLength(barr);
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);

    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);

    return rtn;
}