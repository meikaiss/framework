package com.android.framework.util;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by meikai on 16/7/7.
 */
public class FontUtils {

    private Context context;

    /*
     * 字库名
     */
    private static String dotMatrixFont = "HZK16";
    ;
    /*
     * 编码 GB2312
     */
    private final static String ENCODE = "GB2312";

    /*
     * 16X16的字库用16个点表示
     */
    private int dots = 16;

    /*
     * 一个字用点表示需要多少字节，16X16的字体需要32个字节
     */
    private int wordByteByDots = 32;

    public FontUtils(Context context) {
        this.context = context;
    }

    /**
     * 获取字符串的点阵矩阵
     *
     * @param str
     * @return
     */
    public boolean[][] getWordsInfo(String str) {
        byte[] dataBytes = null;
        try {
            dataBytes = str.getBytes(ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 汉字对应的byte数组
        int[] byteCode = new int[dataBytes.length];
        // 当成无符号对待
        for (int i = 0; i < dataBytes.length; i++) {
            // 根据每两个byte数组计算一个汉字的相对位置
            byteCode[i] = dataBytes[i] < 0 ? 256 + dataBytes[i] : dataBytes[i];
        }
        // 用来存放所有汉字对应的字模信息
        // 一个汉字32 byte
        int wordNums = byteCode.length / 2;
        boolean[][] matrix = new boolean[dots][wordNums * dots];
        byte[] dataResult = new byte[wordNums * wordByteByDots];
        for (int i = 0, numIndex = 0; i < byteCode.length; i += 2, numIndex++) {
            // 依次读取到这个汉字对应的32位的字模信息
            byte[] data = read(byteCode[i], byteCode[i + 1]);
            System.arraycopy(data, 0, dataResult, numIndex * data.length, data.length);
        }
        for (int num = 0; num < wordNums; num++) {
            for (int i = 0; i < dots; i++) {
                for (int j1 = 0; j1 < 2; j1++) {
                    // 对每个字节进行解析
                    byte tmp = dataResult[num * wordByteByDots + i * 2 + j1];
                    for (int j2 = 0; j2 < 8; j2++) {
                        if (((tmp >> (7 - j2)) & 1) == 1) {
                            matrix[i][num * dots + j1 * 8 + j2] = true;
                        } else {
                            matrix[i][num * dots + j1 * 8 + j2] = false;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    /**
     * 获取一个汉字的点阵数组，开始测试代码时用。。
     *
     * @param str
     * @param font
     * @return
     */
    private boolean[][] getWordInfo(String str, String font) {
        boolean[][] matrix = new boolean[16][16];
        int[] byteCode = new int[2];
        try {
            byte[] data = str.getBytes(ENCODE);
            // 当成无符号对待
            byteCode[0] = data[0] < 0 ? 256 + data[0] : data[0];
            byteCode[1] = data[1] < 0 ? 256 + data[1] : data[1];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 读取到这个汉子对应的32位的字模信息
        byte[] data = read(byteCode[0], byteCode[1]);
        // 填充16x16的矩阵
        for (int i = 0; i < dots; i++) {
            for (int j1 = 0; j1 < 2; j1++) {
                // 对每个字节进行解析
                byte tmp = data[i * 2 + j1];
                for (int j2 = 0; j2 < 8; j2++) {
                    if (((tmp >> (7 - j2)) & 1) == 1) {
                        matrix[i][j1 * 8 + j2] = true;
                    } else {
                        matrix[i][j1 * 8 + j2] = false;
                    }
                }
            }
        }
        return matrix;
    }

    /**
     * 从字库中找到这个汉字的字模信息
     *
     * @param areaCode 区码，对应编码的第一个字节
     * @param posCode  位码，对应编码的第二个字节
     * @return
     */
    protected byte[] read(int areaCode, int posCode) {
        byte[] data = null;
        try {
            int area = areaCode - 0xa0;
            int pos = posCode - 0xa0;
            InputStream in = context.getResources().getAssets().open(dotMatrixFont);
            long offset = wordByteByDots * ((area - 1) * 94 + pos - 1);
            in.skip(offset);
            data = new byte[wordByteByDots];
            in.read(data, 0, wordByteByDots);
            in.close();
        } catch (Exception ex) {
            Log.e("FontUtil", ex.getMessage());
        }
        return data;
    }

}