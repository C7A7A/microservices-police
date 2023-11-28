package com.police.policemen.data;

import java.util.List;

public class EquippedPoliceman {
    private Policeman policeman;
    private List<Weapon> weapons;

    public EquippedPoliceman() {
    }

    public EquippedPoliceman(Policeman policeman, List<Weapon> weapons) {
        this.policeman = policeman;
        this.weapons = weapons;
    }

    public Policeman getPoliceman() {
        return policeman;
    }

    public void setPoliceman(Policeman policeman) {
        this.policeman = policeman;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
