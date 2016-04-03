//
// Created by meikai on 16/3/31.
//

#include<string.h>
#include<jni.h>

#ifndef FRAMEWORK_ENCRYPT_H
#define FRAMEWORK_ENCRYPT_H



//计算字符对应的byte值
unsigned char getByteNumber( char first,  char end);


//加密函数
void encrypt( char p[],  char res[], int pLen);


//解密函数
void decrypt( char p[], char res[], int pLen);


#endif //FRAMEWORK_ENCRYPT_H