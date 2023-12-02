package com.police.emergencies.data;

import com.police.basedomains.policemen.EquippedPoliceman;
import com.police.basedomains.vehicles.GetVehicleListResponse;

import java.util.List;

public class PolicemenVehicles {
    private List<EquippedPoliceman> equippedPolicemen = null;
    private GetVehicleListResponse vehicles = null;

    public PolicemenVehicles(List<EquippedPoliceman> equippedPolicemen, GetVehicleListResponse vehicles) {
        this.equippedPolicemen = equippedPolicemen;
        this.vehicles = vehicles;
    }

    public PolicemenVehicles(List<EquippedPoliceman> equippedPolicemen) {
        this.equippedPolicemen = equippedPolicemen;
    }

    public PolicemenVehicles(GetVehicleListResponse vehicles) {
        this.vehicles = vehicles;
    }

    public PolicemenVehicles() {
    }

    public List<EquippedPoliceman> getEquippedPolicemen() {
        return equippedPolicemen;
    }

    public void setEquippedPolicemen(List<EquippedPoliceman> equippedPolicemen) {
        this.equippedPolicemen = equippedPolicemen;
    }

    public GetVehicleListResponse getVehicles() {
        return vehicles;
    }

    public void setVehicles(GetVehicleListResponse vehicles) {
        this.vehicles = vehicles;
    }
}
