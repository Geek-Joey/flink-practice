package com.joey;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

/**
 * 读取Clickhouse数据,打印输出
 * @author wangxc
 */
public class ClickhousePrint {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .inBatchMode()
                .build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env,settings);

        //读取配置参数
        // --startTime 2022-04-10 --endTime 2022-04-11 --locationID 611042028003
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String startTime = parameterTool.get("startTime");
        String endTime = parameterTool.get("endTime");
        String locationID = parameterTool.get("locationID");

        //register ck table
        tableEnv.executeSql("CREATE TABLE input_kako (\n" +
                "    shotTime TimeStamp,\n" +
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
                "    'sink.flush-interval' = '30s',\n" +
                "    'sink.max-retries' = '3'\n" +
                ")");

        // table转换为stream输出
        Table table = tableEnv.sqlQuery("select \n" +
                "    shotTime,\n" +
                "    motorVehicleID,\n" +
                "    locationID,\n" +
                "    locationGBID,\n" +
                "    plateClass,\n" +
                "    plateNo \n" +
                "from input_kako \n " +
                "where shotTime > ' " + startTime + "' and shotTime < '" + endTime + "' and locationID ='" + locationID + "'"

        );

        DataStream<Row> dataStream = tableEnv.toDataStream(table);
        dataStream.print();

        env.execute("Clickhouse Print");

    }
}
