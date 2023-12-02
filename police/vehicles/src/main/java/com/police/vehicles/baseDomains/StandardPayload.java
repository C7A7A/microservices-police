package com.police.vehicles.baseDomains;

import java.util.UUID;

public class StandardPayload {
    private String payloadId;
    private String payload;

    public StandardPayload() {
    }

    public StandardPayload(String payload) {
        this.payloadId = UUID.randomUUID().toString();
        this.payload = payload;
    }

    public StandardPayload(String payloadId, String payload) {
        this.payloadId = payloadId;
        this.payload = payload;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return String.format("%s: (payloadId: %s payload: %s)", getClass().getName(), payloadId, payload);
    }
}
