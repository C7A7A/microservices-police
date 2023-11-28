package com.police.emergencies.data;

public class Emergency {
    private Type type;
    private String date;
    private String time;
    private String address;
    private Integer injuredAmount;
    private Integer offendersAmount;

    public Emergency() {
    }

    public Emergency(Type type, String date, String time, String address, Integer injuredAmount, Integer offendersAmount) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.address = address;
        this.injuredAmount = injuredAmount;
        this.offendersAmount = offendersAmount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getInjuredAmount() {
        return injuredAmount;
    }

    public void setInjuredAmount(Integer injuredAmount) {
        this.injuredAmount = injuredAmount;
    }

    public Integer getOffendersAmount() {
        return offendersAmount;
    }

    public void setOffendersAmount(Integer offendersAmount) {
        this.offendersAmount = offendersAmount;
    }
}
