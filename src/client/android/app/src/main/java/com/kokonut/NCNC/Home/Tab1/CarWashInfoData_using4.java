package com.kokonut.NCNC.Home.Tab1;

import java.util.List;

public class CarWashInfoData_using4 {
    private String name;
    private String address;
    private String opentime;
    private String wash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return opentime;
    }

    public void setOpenTime(String opentime) {
        this.opentime = opentime;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    public CarWashInfoData_using4(String name, String address, String opentime, String wash) {
        this.name = name;
        this.address = address;
        this.opentime = opentime;
        this.wash = wash;
    }
}