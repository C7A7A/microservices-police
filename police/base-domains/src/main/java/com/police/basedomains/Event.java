package com.police.basedomains;

public class Event {
    private EventType eventType;
    private String payload;
    private String payloadId;

    public Event() {
    }

    public Event(EventType eventType, String payload) {
        this.eventType = eventType;
        this.payload = payload;
        this.payloadId = null;
    }

    public Event(EventType eventType, String payload, String payloadId) {
        this.eventType = eventType;
        this.payload = payload;
        this.payloadId = payloadId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }
}
