//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.23 at 02:12:52 PM CET 
//


package com.police.basedomains.vehicles;

import java.util.ArrayList;
import java.util.List;

public class GetVehicleListResponse {

    protected List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() {
        if (vehicles == null) {
            vehicles = new ArrayList<Vehicle>();
        }
        return this.vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        if (vehicles == null) {
            vehicles = new ArrayList<>();
        }

        this.vehicles = vehicles;
    }
}
