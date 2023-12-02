package com.police.emergencies.data;

import com.police.basedomains.policemen.EquippedPoliceman;
import com.police.basedomains.vehicles.GetVehicleListResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class FullDataEmergency {
    private HashMap<String, PolicemenVehicles> data = new HashMap<>();

    public FullDataEmergency() {
    }

    public FullDataEmergency(String payloadId, PolicemenVehicles policemenVehicles) {
        data.put(payloadId, policemenVehicles);
    }

    public HashMap<String, PolicemenVehicles> getData() {
        return data;
    }

    public void setData(String payloadId, List<EquippedPoliceman> equippedPolicemen) {
        data.put(payloadId, new PolicemenVehicles(equippedPolicemen));
    }

    public void setData(String payloadId, GetVehicleListResponse vehicles) {
        data.put(payloadId, new PolicemenVehicles(vehicles));
    }

    public boolean checkIfExist(String payloadId) {
        PolicemenVehicles policemenVehicles = data.get(payloadId);

        if (policemenVehicles == null) {
            return false;
        }

        return policemenVehicles.getEquippedPolicemen() != null || policemenVehicles.getVehicles() != null;
    }

    public boolean checkIfVehiclesSaved(String payloadId) {
        PolicemenVehicles policemenVehicles = data.get(payloadId);

        if (policemenVehicles == null) {
            return false;
        }

        return policemenVehicles.getVehicles() != null;
    }

    public boolean checkIfPolicemenSaved(String payloadId) {
        PolicemenVehicles policemenVehicles = data.get(payloadId);

        if (policemenVehicles == null) {
            return false;
        }

        return policemenVehicles.getEquippedPolicemen() != null;
    }
}
