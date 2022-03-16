package com.joey.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** 调用RESTful接口 */
public class RestfulUtils {
  public static void main(String[] args) {
    String url = "http://192.168.202.157:8080/greeting?name=Flink";
    String response = RestfulUtils.getRequest(url);
    //String response = RestfulUtils.postRequest(url,"{}");
    System.out.println(response);
  }

  // GET 请求
  public static String getRequest(String url) {
    StringBuffer result = new StringBuffer();

    HttpURLConnection conn = null;
    OutputStream out = null;
    BufferedReader reader = null;
    try {
      URL restUrl = new URL(url);
      conn = (HttpURLConnection) restUrl.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty(
          "user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

      conn.connect();

      int responseCode = conn.getResponseCode();
      if (responseCode != 200) {
        throw new RuntimeException("Error responseCode:" + responseCode);
      }

      String output = null;
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      while ((output = reader.readLine()) != null) {
        result.append(output);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(" 调用接口出错 ");
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        if (out != null) {
          out.close();
        }
        if (conn != null) {
          conn.disconnect();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }

    return result.toString();
  }

  // POST 请求
  public static String postRequest(String url,String jsonStr) {
    StringBuffer result = new StringBuffer();

    HttpURLConnection conn = null;
    OutputStream out = null;
    BufferedReader reader = null;
    try {
      URL restUrl = new URL(url);
      conn = (HttpURLConnection) restUrl.openConnection();
      conn.setRequestMethod("POST");
      conn.setConnectTimeout(3000);
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

      conn.connect();

      //将参数写入请求中
      if (jsonStr != null && jsonStr.length() > 0) {
        byte[] bytes = jsonStr.getBytes();
        //设置文件长度
        conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
        OutputStream outstream = conn.getOutputStream();
        outstream.write(jsonStr.getBytes());
        outstream.flush();
        outstream.close();
      }

      int responseCode = conn.getResponseCode();
      if (responseCode != 200) {
        throw new RuntimeException("Error responseCode:" + responseCode);
      }

      String output = null;
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      while ((output = reader.readLine()) != null) {
        result.append(output);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(" 调用接口出错 ");
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        if (out != null) {
          out.close();
        }
        if (conn != null) {
          conn.disconnect();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result.toString();
  }
}
