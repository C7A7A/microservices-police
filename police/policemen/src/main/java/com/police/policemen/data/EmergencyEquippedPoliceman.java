package com.police.policemen.data;

import com.police.basedomains.EquippedPoliceman;

import java.util.List;

public class EmergencyEquippedPoliceman {
    private String payloadId;
    private List<EquippedPoliceman> equippedPolicemen;

    public EmergencyEquippedPoliceman() {
    }

    public EmergencyEquippedPoliceman(String payloadId, List<EquippedPoliceman> equippedPolicemen) {
        this.payloadId = payloadId;
        this.equippedPolicemen = equippedPolicemen;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public List<EquippedPoliceman> getEquippedPolicemen() {
        return equippedPolicemen;
    }

    public void setEquippedPolicemen(List<EquippedPoliceman> equippedPolicemen) {
        this.equippedPolicemen = equippedPolicemen;
    }
}
