package com.joey;

import com.alibaba.fastjson.JSON;
import com.joey.entity.KakoVehicle;
import com.joey.utils.StringUtils;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 * 模拟数据，发送到kafka
 * @author joey
 * @create 2022-02-17 10:42 AM
 */
public class KafkaProducerDemo {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //1.source,传入自定义的source
        //DataStreamSource<String> source = env.addSource(new MyDataSource()).setParallelism(1);
        DataStreamSource<String> source = env.addSource(new KakoDataSource()).setParallelism(1);

        //2.set kafka properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "172.16.1.193:9092");

        KafkaSerializationSchema<String> serializationSchema = new KafkaSerializationSchema<String>() {
            @Override
            public ProducerRecord<byte[], byte[]> serialize(String element, @Nullable Long timestamp) {

                return new ProducerRecord<>(
                  "test-flink",
                  element.getBytes(StandardCharsets.UTF_8)
                );
            }
        };
        //3.create fink producer
        FlinkKafkaProducer flinkKafkaProducer = new FlinkKafkaProducer(
                "abc",
                serializationSchema,
                properties,
                FlinkKafkaProducer.Semantic.EXACTLY_ONCE
        );
        //4.kafka sink
        source.addSink(flinkKafkaProducer);

        //5.execute
        env.execute("kafka producer");

    }

    /**
     * 自定义发送器，模拟数据发送到kafka
     */
    public static class MyDataSource implements SourceFunction<String> {
        private boolean isRunning = true;
        @Override
        public void run(SourceContext<String> sourceContext) throws Exception {
            //循环发送数据
            while (isRunning) {
                //list
                ArrayList<String> list = new ArrayList<>();
                list.add("zhangsan");
                list.add("lisi");
                list.add("wangwu");
                list.add("zhaoliu");
                list.add("qianqi");

                //随机访问list
                int index = new Random().nextInt(5);

                //发送数据
                sourceContext.collect(list.get(index));

                //wait，每2秒发送一条
                Thread.sleep(2000);

            }
        }

        @Override
        public void cancel() {
            isRunning = false;
        }
    }


    /**
     * 卡口数据生成，写入kafka
     */
    public static class KakoDataSource implements SourceFunction<String>{
        private boolean isRunning = true;

        @Override
        public void run(SourceContext<String> sourceContext) throws Exception {
            while (isRunning) {
                String data = getRandmKakoVehicle();
                sourceContext.collect(data);
                Thread.sleep(2000);
            }
        }

        @Override
        public void cancel() {
            isRunning = false;
        }
    }

    /**
     * 返回随机生成的json(KakoVehicle)
     * @return json
     */
    public static String getRandmKakoVehicle() {
        String motorVehicleID = StringUtils.getRandomString(48);
        int infoKind = new Random().nextInt(2);
        KakoVehicle<String, Number> vehicle = new KakoVehicle<String, Number>(motorVehicleID,infoKind);
        String jsonString = JSON.toJSONString(vehicle);
        return jsonString;
    }
}
