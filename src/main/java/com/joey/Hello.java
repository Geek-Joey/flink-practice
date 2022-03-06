package com.joey;

import com.alibaba.fastjson.JSON;
import com.joey.entity.KakoVehicle;

import java.util.Random;

/**
 * @author joey
 * @create 2022-02-16 10:18 PM
 */
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello Flink !");

        //test lombok getter
        KakoVehicle device = new KakoVehicle("3121213516263");
        String motorVehicleID = device.getMotorVehicleID();
        System.out.println("Lombok: " + motorVehicleID);

        //test getRandomString method
        String randStr = getRandomString(48);
        System.out.println("随机生成48位数字字符串：" + randStr);

        //test FastJson
        KakoVehicle vehicle = new KakoVehicle("3121213516263", 1);
        String jsonStr = JSON.toJSONString(vehicle);
        System.out.println("Object->JSON: " + jsonStr);

        // 持续生成KakoVehicle json数据
        System.out.println("-----------生成JSON数据---------------");
        int count = 0;

        while (true) {

            KakoVehicle obj = getRandmKakoVehicle();
            String json = JSON.toJSONString(obj);
            count ++;
            System.out.println(count + ":" + json);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 生成随机n位数字的字符串
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int x = random.nextInt(9);
            stringBuffer.append(str.charAt(x));
        }
        return stringBuffer.toString();
    }

    /**
     * 随机生成一个实例 KakoVehicle
     * @return
     */
    public static KakoVehicle getRandmKakoVehicle() {
        String motorVehicleID = getRandomString(48);
        int infoKind = new Random().nextInt(2);
        KakoVehicle vehicle = new KakoVehicle(motorVehicleID,infoKind);
        return vehicle;
    }
}
