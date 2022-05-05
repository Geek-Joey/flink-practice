package com.joey;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.Properties;

/**
 * @author joey
 * @create 2022-03-17 6:48 下午
 */
public class MyTest {

    public static void main(String[] args) throws Exception{

//        for (int i=0;i<1000;i++) {
//            System.out.print("gather_track_info" + i + ",");
//        }

        //Tuple0~Tuple25
        Tuple2<String, Integer> person = Tuple2.of("zhangsan", 23);
        String name = person.f0;
        Integer age = person.f1;
        System.out.println("name: " + name + " , age: " + age);
        System.out.println(person);

        //测试ParameterTool
        ParameterTool parameters = ParameterTool.fromSystemProperties();
        Properties properties = parameters.getProperties();
        System.out.println(properties);

        //
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile("src/main/resources/application.properties");
        String parallelism = parameterTool.get("mapParallelism","2");
        System.out.println("mapParallelism: " + parallelism);
    }

}
