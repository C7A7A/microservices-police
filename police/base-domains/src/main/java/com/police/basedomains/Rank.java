package com.police.basedomains;

public enum Rank {

    CONSTABLE("constable", 0),
    SENIOR_CONSTABLE("senior constable", 1),
    SERGEANT("sergeant", 2),
    SENIOR_SERGEANT("senior sergeant", 3),
    STAFF_SERGEANT("staff sergeant", 4),
    JUNIOR_ASPIRANT("junior aspirant", 5),
    ASPIRANT("aspirant", 6),
    SENIOR_ASPIRANT("senior aspirant", 7),
    STAFF_ASPIRANT("staff aspirant", 8);

    private final String rank;
    private final Integer level;

    Rank(String rank, Integer level) {
        this.rank = rank;
        this.level = level;
    }

    public String getRank() {
        return this.rank;
    }

    public Integer getLevel() {
        return level;
    }
}
