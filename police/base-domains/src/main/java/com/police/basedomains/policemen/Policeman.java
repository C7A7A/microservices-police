package com.police.basedomains.policemen;

public class Policeman {
    private String name;
    private String surname;
    private Integer age;
    private Rank rank;

    public Policeman() {
    }

    public Policeman(String name, String surname, Integer age, Rank rank) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
