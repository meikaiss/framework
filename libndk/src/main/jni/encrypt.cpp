//
// Created by meikai on 16/3/31.
//

#include "encrypt.h"


const char key[] = "abcdefghijklmnop"; //16个字符

//计算字符对应的byte值
unsigned char getByteNumber(char first, char end) {
    int firstPosition = 0, endPosition = 0;
    int position = 0;
    for (; position < 16; position++) {
        if (key[position] == first) {
            firstPosition = position;
        }
        if (key[position] == end) {
            endPosition = position;
        }
    }
    return (firstPosition << 4) | (endPosition);
}

//加密函数
void encrypt(char p[], char res[], int pLen) {
    int i = 0;
    for (; i < pLen; i++) {
        res[2 * i] = key[p[i] / 16];
        res[2 * i + 1] = key[p[i] % 16];
    }
}

//解密函数
void decrypt(char p[], char res[] , int pLen) {
    int i;
    for (i = 0; i < pLen; i++) {
        res[i] = getByteNumber(p[i * 2], p[i * 2 + 1]);
    }
}