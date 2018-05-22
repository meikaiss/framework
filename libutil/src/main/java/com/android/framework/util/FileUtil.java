package com.android.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by meikai on 2018/05/09.
 */
public class FileUtil {

    /**
     * 判断某文件内容是否为gif格式的图片
     * 不通过文件名和后缀名判断，因为后缀名可能被修改，不是确切可靠的文件类型依据
     */
    private boolean isGifFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int[] flags = new int[5];
            flags[0] = inputStream.read();
            flags[1] = inputStream.read();
            flags[2] = inputStream.read();
            flags[3] = inputStream.read();
            inputStream.skip(inputStream.available() - 1);
            flags[4] = inputStream.read();
            inputStream.close();
            return flags[0] == 71 && flags[1] == 73 && flags[2] == 70 && flags[3] == 56 && flags[4] == 0x3B;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isJpgFile(File file) {
        try {
            FileInputStream bin = new FileInputStream(file);
            int b[] = new int[4];
            b[0] = bin.read();
            b[1] = bin.read();
            bin.skip(bin.available() - 2);
            b[2] = bin.read();
            b[3] = bin.read();
            bin.close();
            return b[0] == 255 && b[1] == 216 && b[2] == 255 && b[3] == 217;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
