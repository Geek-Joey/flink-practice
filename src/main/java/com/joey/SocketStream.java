package com.joey;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author joey
 * @create 2022-02-24 11:29 PM
 */
public class SocketStream {
    public static void main(String[] args) throws Exception{
        // TODO 1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //并行度
        env.setParallelism(4);

        // TODO 2.监听端口流数据
        DataStream<String> dataSource = env.socketTextStream("localhost", 8888);

        // TODO 3.基于数据流进行计算
        DataStream<Tuple2<String, Integer>> ds = dataSource.flatMap(new MySpliter())
                .keyBy(0)
                .sum(1);

        // TODO 4.打印输出
        ds.print();

        // TODO 5.执行任务
        env.execute("Flink WordCount");

    }

    /**
     * 自定义flatmap实现
     */
    public static class MySpliter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String str, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] words = str.split(" ");
            for (String word : words) {
                out.collect(new Tuple2<>(word,1));
            }
        }
    }
}
