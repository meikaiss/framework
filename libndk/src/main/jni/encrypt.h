//
// Created by meikai on 16/3/31.
//

#include<string.h>
#include<jni.h>

#ifndef FRAMEWORK_ENCRYPT_H
#define FRAMEWORK_ENCRYPT_H


//加密函数
char *encrypt(char p[], int pLen);


//解密函数
char *decrypt(char encryptChar[], int pLen);


//计算字符对应的byte值
unsigned char getByteNumber(char first, char end);



#endif //FRAMEWORK_ENCRYPT_H