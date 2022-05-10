package com.joey;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 读写HDFS
 * @author joey
 * @create 2022-05-05 5:37 下午
 */
public class HDFSClient {


    public static void main(String[] args) throws Exception{
//        String filePath = "/tmp/hb-kako-vehicle-recognition-2021-05.json";
//        HDFSClient.readHDFSFile(filePath);
        HDFSClient.writeHDFS("input/example.txt","hdfs://172.16.1.192:9000/tmp");

    }

    public static FileSystem getFiledSystem() throws IOException {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(configuration);
        return fileSystem;
    }



    /**
     * 读取HDFS文件数据
     * @param filePath
     */
    public static void readHDFSFile(String filePath){
        FSDataInputStream fsDataInputStream = null;

        try {
            Path path = new Path(filePath);
            fsDataInputStream = getFiledSystem().open(path);
            IOUtils.copyBytes(fsDataInputStream, System.out, 4096, false);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fsDataInputStream != null){
                IOUtils.closeStream(fsDataInputStream);
            }
        }

    }

    /**
     * 将本地文件写到HDFS上
     * @param localPath
     * @param hdfsPath
     */
    public static void writeHDFS(String localPath, String hdfsPath){
        FSDataOutputStream outputStream = null;
        FileInputStream fileInputStream = null;

        try {
            Path path = new Path(hdfsPath);
            outputStream = getFiledSystem().create(path);
            fileInputStream = new FileInputStream(new File(localPath));
            //输入流、输出流、缓冲区大小、是否关闭数据流，如果为false就在 finally里关闭
            IOUtils.copyBytes(fileInputStream, outputStream,4096, false);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null){
                IOUtils.closeStream(fileInputStream);
            }
            if(outputStream != null){
                IOUtils.closeStream(outputStream);
            }
        }

    }


}
