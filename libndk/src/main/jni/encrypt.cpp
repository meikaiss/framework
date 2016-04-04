//
// Created by meikai on 16/3/31.
//

#include "encrypt.h"


const char key[] = "abcdefghijklmnop"; //16个字符


//加密函数
char *encrypt(char sourceChar[], int pLen) {
    char *encrypt = new char[pLen * 2];

    int i = 0;
    for (; i < pLen; i++) {
        encrypt[2 * i] = key[sourceChar[i] / 16];
        encrypt[2 * i + 1] = key[sourceChar[i] % 16];
    }
    encrypt[pLen * 2] = '\0';

    return encrypt;
}

//解密函数
char *decrypt(char encryptChar[], int pLen) {
    char *decryptChar = new char[pLen];

    int i;
    for (i = 0; i < pLen; i++) {
        decryptChar[i] = getByteNumber(encryptChar[i * 2], encryptChar[i * 2 + 1]);
    }

    decryptChar[pLen] = '\0';

    return decryptChar;
}

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