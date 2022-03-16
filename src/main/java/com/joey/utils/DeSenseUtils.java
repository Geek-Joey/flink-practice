package com.joey.utils;

/** 数据脱敏工具类 */
public class DeSenseUtils {
  public static void main(String[] args) {
    String str = "371102199502257788";
    String desenseStr = DeSenseUtils.deSense(str, 1, 1, "*");
    System.out.println("[" + str + "," + desenseStr + "]");

    String str1 = "123456789";
    String mask = mask(str1, 2, 7);
    System.out.println(mask);
    System.out.println(mask(str1, 3, 6));
    System.out.println(mask(str1, 3, 6, "#"));
  }

  /**
   * @deprecated 已废弃
   * @param origin 原始字符串
   * @param prefixNoMaskLen 左边保留几位
   * @param suffixNoMaskLen 右边保留几位
   * @param maskStr 掩盖符号
   * @return
   */
  public static String deSense(
      String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
    if (origin == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0, n = origin.length(); i < n; i++) {
      if (i < prefixNoMaskLen) {
        sb.append(origin.charAt(i));
        continue;
      }
      if (i > (n - suffixNoMaskLen - 1)) {
        sb.append(origin.charAt(i));
        continue;
      }
      sb.append(maskStr);
    }
    return sb.toString();
  }

  /**
   * 默认使用*代替
   *
   * @param text 文本字符串
   * @param left 左边的位置
   * @param len 长度
   * @return
   */
  public static String mask(String text, int left, int len) {
    StringBuilder builder = new StringBuilder();

    if (text != null && left > 0 && (left + len) <= text.length()) {
      for (int i = 0; i < left - 1; i++) {
        builder.append(text.charAt(i));
      }
      for (int j = 0; j < len; j++) {
        builder.append("*");
      }
      for (int k = left + len - 1; k < text.length(); k++) {
        builder.append(text.charAt(k));
      }
      return builder.toString();
    }
    return null;
  }

  /**
   * 使用maskStr代替，
   *
   * @param text
   * @param left
   * @param len
   * @param maskStr
   * @return
   */
  public static String mask(String text, int left, int len, String maskStr) {
    StringBuilder builder = new StringBuilder();

    if (text != null && left > 0 && (left + len) <= text.length()) {
      for (int i = 0; i < left - 1; i++) {
        builder.append(text.charAt(i));
      }
      for (int j = 0; j < len; j++) {
        builder.append(maskStr);
      }
      for (int k = left + len - 1; k < text.length(); k++) {
        builder.append(text.charAt(k));
      }
      return builder.toString();
    }
    return null;
  }
}
