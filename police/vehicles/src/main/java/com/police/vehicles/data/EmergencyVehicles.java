package com.police.vehicles.data;

import com.police.vehicles.data.GetVehicleListResponse;

public class EmergencyVehicles {
    private String payloadId;
    private GetVehicleListResponse vehicles;

    public EmergencyVehicles() {
    }

    public EmergencyVehicles(String payloadId, GetVehicleListResponse vehicles) {
        this.payloadId = payloadId;
        this.vehicles = vehicles;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public GetVehicleListResponse getVehicles() {
        return vehicles;
    }

    public void setVehicles(GetVehicleListResponse vehicles) {
        this.vehicles = vehicles;
    }
}
