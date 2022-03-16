package com.joey.utils;

import java.io.*;
import java.util.*;

/** 随机取样 工具类 */
public class RandomSampleUtils {

  public static void main(String[] args) {
    String filePath = "input/student.txt";
    Double seed = 0.7;
    String sampleData = getSampleData(filePath, seed);
    System.out.println(sampleData);
  }

  /**
   * 读取文件路径，随机抽取数据 1. 读取需要随机抽样的信息文件 2. 统计出该文件的行数（一行对应一条信息） 3. 通过随机算法产生需要抽取样本的行号 4. 将结果输出
   *
   * @param filePath
   * @return
   */
  public static String getSampleData(String filePath, Double seed) {
    BufferedReader reader = null;
    StringBuilder builder = new StringBuilder();
    int totalLines = getTotalLines(filePath);
    int readNums = (int) (totalLines * seed);

    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
      for (int i = 0; i < readNums; i++) {
        Random random = new Random();
        // 随机获取读取的行数
        int lineNumber = (int) (random.nextDouble() * totalLines);
        String line = reader.readLine();
        builder.append(line + ",");
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return builder.toString();
  }

  /**
   * 获取文件内容的总行数
   *
   * @param fileName
   * @return
   */
  public static int getTotalLines(String fileName) {
    BufferedReader in = null;
    int lines = 0;
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
      LineNumberReader reader = new LineNumberReader(in);
      String s = reader.readLine();
      while (s != null) {
        lines++;
        s = reader.readLine();
      }
      reader.close();
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return lines;
  }
}
