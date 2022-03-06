package com.joey;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * streaming window word count application
 * that counts the words coming from a web socket in 5 second windows
 * @author joey
 * @create 2022-02-17 9:09 AM
 */
public class WindowWordCount {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dataStream = env.socketTextStream("localhost", 9999)
                .flatMap(new Spliter())
                .keyBy(value -> value.f0)
                .sum(1);

        dataStream.print();
        env.execute("Window wordCount");
    }


    public static class Spliter implements FlatMapFunction<String, Tuple2<String,Integer>> {

        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] words = sentence.split(" ");
            for (String word : words) {
                out.collect(new Tuple2<String,Integer>(word,1));
            }
        }
    }
}
