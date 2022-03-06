package com.joey.utils;

import java.util.Random;

/**
 * @author joey
 * @create 2022-02-17 2:11 PM
 */
public class StringUtils {
    /**
     * 生成随机n位数字的字符串
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int x = random.nextInt(9);
            stringBuffer.append(str.charAt(x));
        }
        return stringBuffer.toString();
    }
}
