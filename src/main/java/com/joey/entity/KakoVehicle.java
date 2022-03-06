package com.joey.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * @author joey
 * @create 2022-02-17 11:45 AM
 */

@Getter
@Setter
public class KakoVehicle {
    private String motorVehicleID;
    private int infoKind;

    public KakoVehicle() {

    }

    public KakoVehicle(String motorVehicleID, int infoKind) {
        this.motorVehicleID = motorVehicleID;
        this.infoKind = infoKind;
    }

    public KakoVehicle(String motorVehicleID) {
        this.motorVehicleID = motorVehicleID;
    }

    @Override
    public String toString() {
        return "KakoVehicle{" +
                "motorVehicleID='" + motorVehicleID + '\'' +
                ", infoKind=" + infoKind +
                '}';
    }
}
