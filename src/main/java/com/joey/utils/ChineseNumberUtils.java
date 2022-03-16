package com.joey.utils;

/** 中文数字转换工具类 */
public class ChineseNumberUtils {

  public static final String ZERO = "零";
  public static final String ONE = "一";
  public static final String TWO = "二";
  public static final String THREE = "三";
  public static final String FOUR = "四";
  public static final String FIVE = "五";
  public static final String SIX = "六";
  public static final String SEVEN = "七";
  public static final String EIGHT = "八";
  public static final String NINE = "九";
  public static final String POINT = "点";

  // OLD CHINESE NUMBER
  public static final String OLD_ONE = "壹";
  public static final String OLD_TWO = "贰";
  public static final String OLD_THREE = "叁";
  public static final String OLD_FOUR = "肆";
  public static final String OLD_FIVE = "伍";
  public static final String OLD_SIX = "陆";
  public static final String OLD_SEVEN = "柒";
  public static final String OLD_EIGHT = "捌";
  public static final String OLD_NINE = "玖";

  public static final String HUNDRED = "佰";
  public static final String THOUSAND = "千";
  public static final String TEN_THOUSAND = "萬";
  public static final String HUNDRED_THOUSAND = "十萬";
  public static final String MILLION = "佰万";
  public static final String TEN_MILLION = "千万";

  public static final int NEW_TPYE = 0;
  public static final int OLD_TPYE = 1;

  // 测试方法
  public static void main(String[] args) {
    double number = 123.45;
    String chineseNum1 = "一二三点四五";
    String chineseNum2 = "壹佰贰叁点肆伍";

    System.out.println("******数字转中文******");
    String c1 = ChineseNumberUtils.getChineseNumber(number);
    System.out.println("(source,target):" + "(" + number + "," + c1 + ")");
    String c2 = ChineseNumberUtils.getInvoiceNumber(number);
    System.out.println("(source,target):" + "(" + number + "," + c2 + ")");

    System.out.println("******数字转中文******");
    double n1 = ChineseNumberUtils.getSimpleNumber(chineseNum1);
    System.out.println("(source,target):" + "(" + chineseNum1 + "," + n1 + ")");
    double n2 = ChineseNumberUtils.getTraditionalNumber(chineseNum2);
    System.out.println("(source,target):" + "(" + chineseNum2 + "," + n2 + ")");
  }

  /** 将阿拉伯数字转换成中文数字 */
  public static String getChineseNumber(double number) {
    String numStr = String.valueOf(number);
    return handleBytes(numStr, NEW_TPYE);
  }

  /** 将阿拉伯数字转换为发票数字 */
  public static String getInvoiceNumber(double number) {
    StringBuilder sb = new StringBuilder();
    String valueOf = String.valueOf(number);
    byte[] bytes = valueOf.getBytes();
    StringBuilder builder = new StringBuilder();
    StringBuilder builderFloat = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      if (bytes[i] == 46) {
        for (int j = i; j < bytes.length; j++) {
          builderFloat.append(new String(new byte[] {bytes[j]}));
        }
        break;
      }
      builder.append(new String(new byte[] {bytes[i]}));
    }
    StringBuilder result = handleOldType(builder.toString().getBytes(), sb);
    StringBuilder chineseNumber = handleOldTypeIgnoreUnit(builderFloat.toString());
    result.append(chineseNumber);
    return result.toString();
  }

  /** 将简单中文数字转换为数字 */
  public static double getSimpleNumber(String number) {
    if (null == number || "".equals(number)) {
      throw new RuntimeException("number is not be null or blank");
    }
    char[] chars = number.toCharArray();
    char[] everyChar;
    StringBuilder sb = new StringBuilder();
    for (char aChar : chars) {
      everyChar = new char[1];
      everyChar[0] = aChar;
      switch (new String(everyChar)) {
        case POINT:
          sb.append(".");
          break;
        case ZERO:
          sb.append("0");
          break;
        case ONE:
          sb.append("1");
          break;
        case TWO:
          sb.append("2");
          break;
        case THREE:
          sb.append("3");
          break;
        case FOUR:
          sb.append("4");
          break;
        case FIVE:
          sb.append("5");
          break;
        case SIX:
          sb.append("6");
          break;
        case SEVEN:
          sb.append("7");
          break;
        case EIGHT:
          sb.append("8");
          break;
        case NINE:
          sb.append("9");
          break;
        default:
          break;
      }
    }
    return Double.valueOf(sb.toString());
  }

  /** 将发票数字转换为数字 */
  public static double getTraditionalNumber(String number) {
    if (null == number || "".equals(number)) {
      throw new RuntimeException("number is not be null or blank");
    }
    char[] chars = number.toCharArray();
    char[] everyChar;
    StringBuilder sb = new StringBuilder();
    for (char aChar : chars) {
      everyChar = new char[1];
      everyChar[0] = aChar;
      switch (new String(everyChar)) {
        case POINT:
          sb.append(".");
          break;
        case ZERO:
          sb.append("0");
          break;
        case OLD_ONE:
          sb.append("1");
          break;
        case OLD_TWO:
          sb.append("2");
          break;
        case OLD_THREE:
          sb.append("3");
          break;
        case OLD_FOUR:
          sb.append("4");
          break;
        case OLD_FIVE:
          sb.append("5");
          break;
        case OLD_SIX:
          sb.append("6");
          break;
        case OLD_SEVEN:
          sb.append("7");
          break;
        case OLD_EIGHT:
          sb.append("8");
          break;
        case OLD_NINE:
          sb.append("9");
          break;
        default:
          break;
      }
    }
    return Double.valueOf(sb.toString());
  }

  /** 处理具体的Byte[],拼接成想要的格式 */
  private static String handleBytes(String str, int type) {
    StringBuilder sb = new StringBuilder();
    byte[] bytes = str.getBytes();
    if (NEW_TPYE == type) {
      return handelNewType(bytes, sb);
    }
    return handleOldType(bytes, sb).toString();
  }

  /** 处理发票式数字 */
  private static StringBuilder handleOldType(byte[] bytes, StringBuilder sb) {
    for (int i = 0; i < bytes.length; i++) {
      switch (bytes[i]) {
        case 46:
          sb.append(POINT);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 48:
          sb.append(ZERO);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 49:
          sb.append(OLD_ONE);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 50:
          sb.append(OLD_TWO);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 51:
          sb.append(OLD_THREE);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 52:
          sb.append(OLD_FOUR);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 53:
          sb.append(OLD_FIVE);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 54:
          sb.append(OLD_SIX);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 55:
          sb.append(OLD_SEVEN);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 56:
          sb.append(OLD_EIGHT);
          sb = handleUnit(sb, i, bytes.length);
          break;
        case 57:
          sb.append(OLD_NINE);
          sb = handleUnit(sb, i, bytes.length);
          break;
        default:
          break;
      }
    }
    return sb;
  }

  /** 处理数字单位 */
  private static StringBuilder handleUnit(StringBuilder sb, int i, int length) {
    if (length <= 1) {
      return sb;
    }

    switch (length) {
      case 3:
        sb = handleHundred(i, sb);
        break;
      case 4:
        sb = handleThousand(i, sb);
        break;
      case 5:
        sb = handleTenThousand(i, sb);
        break;
      case 6:
        sb = handleHundredThousand(i, sb);
        break;
      case 7:
        sb = handleMillion(i, sb);
        break;
      case 8:
        sb = handleTenMillion(i, sb);
    }
    return sb;
  }

  /** 拼接千万 */
  private static StringBuilder handleTenMillion(int i, StringBuilder sb) {
    if (i == 0) {
      sb.append(TEN_MILLION);
    }
    if (i == 1) {
      sb.append(MILLION);
    }
    if (i == 2) {
      sb.append(HUNDRED_THOUSAND);
    }
    if (i == 3) {
      sb.append(TEN_THOUSAND);
    }
    if (i == 4) {
      sb.append(THOUSAND);
    }
    if (i == 5) {
      sb.append(HUNDRED);
    }
    return sb;
  }

  /** 拼接百万 */
  private static StringBuilder handleMillion(int i, StringBuilder sb) {
    if (i == 0) {
      sb.append(MILLION);
    }
    if (i == 1) {
      sb.append(HUNDRED_THOUSAND);
    }
    if (i == 2) {
      sb.append(TEN_THOUSAND);
    }
    if (i == 3) {
      sb.append(THOUSAND);
    }
    if (i == 4) {
      sb.append(HUNDRED);
    }
    return sb;
  }

  /** 拼接十万 */
  private static StringBuilder handleHundredThousand(int i, StringBuilder sb) {
    if (i == 0) {
      sb.append(HUNDRED_THOUSAND);
    }
    if (i == 1) {
      sb.append(TEN_THOUSAND);
    }
    if (i == 2) {
      sb.append(THOUSAND);
    }
    if (i == 3) {
      sb.append(HUNDRED);
    }
    return null;
  }

  /** 拼接万 */
  private static StringBuilder handleTenThousand(int i, StringBuilder sb) {
    if (i == 0) {
      sb.append(TEN_THOUSAND);
    }
    if (i == 1) {
      sb.append(THOUSAND);
    }
    if (i == 2) {
      sb.append(HUNDRED);
    }
    return sb;
  }

  /** 拼接千位数 */
  private static StringBuilder handleThousand(int i, StringBuilder sb) {
    if (i == 0) {
      sb.append(THOUSAND);
    }
    if (i == 1) {
      sb.append(HUNDRED);
    }
    return sb;
  }

  /** 拼接百位数 */
  private static StringBuilder handleHundred(int i, StringBuilder sb) {
    if (i == 0) {
      sb.append(HUNDRED);
    }
    return sb;
  }

  /** 处理简单中文数字 */
  private static String handelNewType(byte[] bytes, StringBuilder sb) {
    for (byte aByte : bytes) {
      switch (aByte) {
        case 46:
          sb.append(POINT);
          break;
        case 48:
          sb.append(ZERO);
          break;
        case 49:
          sb.append(ONE);
          break;
        case 50:
          sb.append(TWO);
          break;
        case 51:
          sb.append(THREE);
          break;
        case 52:
          sb.append(FOUR);
          break;
        case 53:
          sb.append(FIVE);
          break;
        case 54:
          sb.append(SIX);
          break;
        case 55:
          sb.append(SEVEN);
          break;
        case 56:
          sb.append(EIGHT);
          break;
        case 57:
          sb.append(NINE);
          break;
        default:
          break;
      }
    }
    return sb.toString();
  }

  /** 处理老式发票抬头数字 忽略单位,仅为小数点后的数字 */
  private static StringBuilder handleOldTypeIgnoreUnit(String toString) {
    byte[] bytes = toString.getBytes();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      switch (bytes[i]) {
        case 46:
          sb.append(POINT);
          break;
        case 48:
          sb.append(ZERO);
          break;
        case 49:
          sb.append(OLD_ONE);
          break;
        case 50:
          sb.append(OLD_TWO);
          break;
        case 51:
          sb.append(OLD_THREE);
          break;
        case 52:
          sb.append(OLD_FOUR);
          break;
        case 53:
          sb.append(OLD_FIVE);
          break;
        case 54:
          sb.append(OLD_SIX);
          break;
        case 55:
          sb.append(OLD_SEVEN);
          break;
        case 56:
          sb.append(OLD_EIGHT);
          break;
        case 57:
          sb.append(OLD_NINE);
          break;
        default:
          break;
      }
    }
    return sb;
  }
}
