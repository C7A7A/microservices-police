package com.police.basedomains;

public class Weapon {
    private String name;
    private DangerLevel dangerLevel;

    public Weapon() {
    }

    public Weapon(String name, DangerLevel dangerLevel) {
        this.name = name;
        this.dangerLevel = dangerLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(DangerLevel dangerLevel) {
        this.dangerLevel = dangerLevel;
    }
}
