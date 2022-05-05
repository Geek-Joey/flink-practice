package com.joey.example;

import com.joey.entity.Person;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author joey
 * @create 2022-04-19 3:58 下午
 */
public class PeopleFilter {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Person> streamSource = env.fromElements(
                new Person("Fred", 35),
                new Person("Wilma", 32),
                new Person("Pebbles", 2)
        );

        SingleOutputStreamOperator<Person> adults = streamSource.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.age >= 18;
            }
        });

        adults.print();

        env.execute("");
    }
}
