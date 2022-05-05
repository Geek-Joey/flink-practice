package com.joey;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.time.LocalDateTime;

/**
 * Table & SQL Demo
 * @author joey
 */
public class TabelSQLDemo {
    public static void main(String[] args) throws Exception{
        //blink stream
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // create a user stream
        DataStream<Row> userStream = env
                .fromElements(
                        Row.of(LocalDateTime.parse("2021-08-21T13:00:00"), 1, "Alice"),
                        Row.of(LocalDateTime.parse("2021-08-21T13:05:00"), 2, "Bob"),
                        Row.of(LocalDateTime.parse("2021-08-21T13:10:00"), 2, "Bob"))
                .returns(
                        Types.ROW_NAMED(
                                new String[] {"ts", "uid", "name"},
                                Types.LOCAL_DATE_TIME, Types.INT, Types.STRING));

        // create corresponding tables
        tableEnv.createTemporaryView(
                "UserTable",
                userStream,
                Schema.newBuilder()
                        .column("ts",DataTypes.TIMESTAMP(3))
                        .column("uid",DataTypes.INT())
                        .column("name",DataTypes.STRING())
                        //.watermark("ts","ts - INTERVAL '1' SECOND")
                        .build()
        );

        Table table = tableEnv.sqlQuery("select ts,uid,name,ts from UserTable");
        DataStream<Row> dataStream = tableEnv.toDataStream(table);
        dataStream.print();


        //DataStream<String> dataStream = env.readTextFile("input/example.txt");

        //将dataStream转换为table
        //Table table = tableEnv.fromDataStream(dataStream,"name");
        //Table distinct = table.distinct();
        //TableResult execute = distinct.execute();

        //将dataStream转换为视图,table api中，view和table是等价的
        //tableEnv.createTemporaryView("textView",dataStream);

        // 输出表
        //distinct.insertInto();

        //输出到文件



        //execute.print();

        env.execute("Table SQL Demo");
    }
}
