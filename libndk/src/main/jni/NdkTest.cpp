//
// Created by meikai on 16/3/31.
//


#include "NdkTest.h"
//#include "encrypt.h"

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_getStringInNDK
        (JNIEnv *env, jclass jc)
{
    return env->NewStringUTF("Hello NDK !!!\n this is a string in so");
}

JNIEXPORT jstring JNICALL Java_com_android_framework_ndk_NdkTest_encryptString
        (JNIEnv *env, jclass jc, jbyteArray src)
{

//    unsigned char *buff = (unsigned char*) env->GetByteArrayElements(src, NULL);
//    len = env->GetArrayLength(src);
//    //加密后长度变为原先的2倍
//    unsigned char res[len * 2] = {0};
//    encrypt(buff, res);
//    //此步骤很重要，标志结束
//    res[len * 2] = '\0';
//
//    //使用完毕释放src数组，因为src数组的存在jvm中
////    env->ReleaseByteArrayElements(src, buff, 0);
//
//    //jni中函数将char数组转变为字符串，jni中字符串为jstring，对应java中的String
//    jstring resStr = stoJstring(env, res);
//    return resStr;


    return env->NewStringUTF("Hello NDK !!!\n this is a string after encrypt");
}

//jstring stoJstring(JNIEnv* env, unsigned char* pat)//change type char* into string
//{
//    jclass strClass = env->FindClass("Ljava/lang/String;");
//    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
//    jbyteArray bytes = env->NewByteArray(strlen(pat));
//    env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
//    jstring encoding = env->NewStringUTF("utf-8");
//    return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
//}