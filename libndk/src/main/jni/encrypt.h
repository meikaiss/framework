//
// Created by meikai on 16/3/31.
//

#include<string.h>
#include<jni.h>

#ifndef FRAMEWORK_ENCRYPT_H
#define FRAMEWORK_ENCRYPT_H

const char key[] = "abcdefghijklmnop"; //16个字符
int len = 0;

#endif //FRAMEWORK_ENCRYPT_H


//计算字符对应的byte值
unsigned char getByteNumber(unsigned char first, unsigned char end);


//加密函数
void encrypt(unsigned char p[], unsigned char res[]);


//解密函数
void decrypt(unsigned char p[], char res[]);