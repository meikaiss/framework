//
// Created by meikai on 16/3/31.
//

#include "encrypt.h"


//计算字符对应的byte值
unsigned char getByteNumber(unsigned char first, unsigned char end) {
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
void encrypt(unsigned char p[], unsigned char res[]) {
    int i = 0;
    for (; i < len; i++) {
        res[2 * i] = key[p[i] / 16];
        res[2 * i + 1] = key[p[i] % 16];
    }
}

//解密函数
void decrypt(unsigned char p[], char res[]) {
    int i;
    for (i = 0; i < len; i++) {
        res[i] = getByteNumber(p[i * 2], p[i * 2 + 1]);
    }
}