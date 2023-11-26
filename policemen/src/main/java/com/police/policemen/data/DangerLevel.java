package com.police.policemen.data;

public enum DangerLevel {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private final String level;

    DangerLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return this.level;
    }
}
