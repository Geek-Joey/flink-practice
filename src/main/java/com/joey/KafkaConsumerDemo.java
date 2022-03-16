package com.joey;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joey.entity.KakoVehicle;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;

import java.util.Properties;


/**
 * @author joey
 * @create 2022-03-15 10:32 上午
 */
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("enable.auto.commit","true");
        properties.setProperty("auto.commit.interval.ms","1000");

        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("172.16.1.193:9092")
                .setTopics("test-flink")
                .setGroupId("testFlinkDSGroup")
                //.setStartingOffsets(OffsetsInitializer.earliest())
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setProperties(properties)
                .build();

        DataStream<String> kafkaStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        DataStream<KakoVehicle<String, Integer>> mapStream = kafkaStream.map(new MyMapFunc());

        mapStream.print();


        try {
            env.execute("Kafka Consumer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyMapFunc implements MapFunction<String,KakoVehicle<String,Integer>> {

        @Override
        public KakoVehicle<String, Integer> map(String s) throws Exception {
            JSONObject jsonObject = JSON.parseObject(s);
            String motorVehicleID = jsonObject.getString("motorVehicleID");
            Integer infoKind = jsonObject.getInteger("infoKind");

            return new KakoVehicle<String,Integer>(motorVehicleID,infoKind);
        }
    }
}
