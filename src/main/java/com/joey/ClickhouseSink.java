package com.joey;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * 读取ck,写入ck
 * @author wangxc
 */
public class ClickhouseSink {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inBatchMode().build();

        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env,settings);

        //register table
        tEnv.executeSql("CREATE TABLE input_kako (\n" +
                "    shotTime TIMESTAMP,\n" +
                "    motorVehicleID STRING,\n" +
                "    locationID STRING,\n" +
                "    locationGBID STRING,\n" +
                "    plateClass INT, \n" +
                "    plateNo STRING\n" +
                ") WITH (\n" +
                "    'connector' = 'clickhouse',\n" +
                "    'url' = 'clickhouse://192.168.3.243:8123',\n" +
                "    'database-name' = 'default',\n" +
                "    'table-name' = 'hb_kako_vehicle_recognition',\n" +
                "    'username' = 'default' ,\n" +
                "    'password' = '123456',\n" +
                "    'sink.batch-size' = '1000',\n" +
                "    'sink.flush-interval' = '60',\n" +
                "    'sink.max-retries' = '3'\n" +
                ")");

        //sink table
        tEnv.executeSql("CREATE TABLE output_kako (\n" +
                "    shotTime TIMESTAMP,\n" +
                "    motorVehicleID STRING,\n" +
                "    locationID STRING,\n" +
                "    locationGBID STRING,\n" +
                "    plateClass INT, \n" +
                "    plateNo STRING\n" +
                ") WITH (\n" +
                "    'connector' = 'clickhouse',\n" +
                "    'url' = 'clickhouse://192.168.3.243:8123',\n" +
                "    'database-name' = 'default',\n" +
                "    'table-name' = 'hb_kako_vehicle_recognition_tmp',\n" +
                "    'username' = 'default' ,\n" +
                "    'password' = '123456'\n" +
                ")");

        //insert
        TableResult tableResult = tEnv.executeSql("insert into output_kako select * from input_kako where shotTime > '2022-04-10' and shotTime < '2022-04-11' ");
        //tableResult.print();

        env.execute("Clickhouse Sink");


    }
}
