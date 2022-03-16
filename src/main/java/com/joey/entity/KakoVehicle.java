package com.joey.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author joey
 * @create 2022-02-17 11:45 AM
 */

@Getter
@Setter
public class KakoVehicle<String, I extends Number> {
    private String motorVehicleID;
    private Integer infoKind;

    public KakoVehicle() {

    }

    public KakoVehicle(String motorVehicleID, Integer infoKind) {
        this.motorVehicleID = motorVehicleID;
        this.infoKind = infoKind;
    }

    public KakoVehicle(String motorVehicleID) {
        this.motorVehicleID = motorVehicleID;
    }

    @Override
    public java.lang.String toString() {
        return "KakoVehicle{" +
                "motorVehicleID=" + motorVehicleID +
                ", infoKind=" + infoKind +
                '}';
    }
}
