package com.police.policemen;

import com.police.basedomains.policemen.DangerLevel;
import com.police.basedomains.policemen.Policeman;
import com.police.basedomains.policemen.Rank;
import com.police.basedomains.policemen.Weapon;
import com.police.policemen.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PolicemenRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolicemenRepository.class);
    private static List<EmergencyEquippedPoliceman> emergencyEquippedPolicemanList = new ArrayList<>();

    public List<Policeman> generatePolicemen() {
        return List.of(
                new Policeman("Brent", "Faulkner", 22, Rank.CONSTABLE),
                new Policeman("Leon", "Mejia", 25, Rank.CONSTABLE),
                new Policeman("Bud", "Wu", 20, Rank.CONSTABLE),
                new Policeman("Danial", "Gamble", 30, Rank.SENIOR_CONSTABLE),
                new Policeman("Junior", "Garcia", 29, Rank.SENIOR_CONSTABLE),
                new Policeman("Jeffrey ", "Jennings", 33, Rank.ASPIRANT),
                new Policeman("Bobbie", "Robbins", 35, Rank.ASPIRANT),
                new Policeman("Bennie", "Riddle", 31, Rank.SERGEANT),
                new Policeman("Toby", "Schmitt", 44, Rank.SENIOR_SERGEANT),
                new Policeman("Xavier", "Tucker", 51, Rank.SERGEANT),
                new Policeman("Carol", "Huynh", 56, Rank.STAFF_SERGEANT),
                new Policeman("Kathrine", "Deleon", 33, Rank.JUNIOR_ASPIRANT),
                new Policeman("Kathie", "Mcconnell", 46, Rank.SENIOR_ASPIRANT),
                new Policeman("Leila", "Morse", 38, Rank.STAFF_ASPIRANT),
                new Policeman("Emilia", "Salinas", 25, Rank.CONSTABLE)
        );
    }

    public List<Weapon> generateWeapons() {
        return List.of(
                new Weapon("Baton", DangerLevel.LOW),
                new Weapon("Handcuffs", DangerLevel.LOW),
                new Weapon("Taser", DangerLevel.MEDIUM),
                new Weapon("Shield", DangerLevel.MEDIUM),
                new Weapon("Handgun", DangerLevel.MEDIUM),
                new Weapon("Rifle", DangerLevel.HIGH),
                new Weapon("Submachine", DangerLevel.HIGH)
        );
    }

    public void saveEmergency(EmergencyEquippedPoliceman emergencyEquippedPoliceman) {
        emergencyEquippedPolicemanList.add(emergencyEquippedPoliceman);
        LOGGER.info(String.format("ADD, size: %s", emergencyEquippedPolicemanList.size()));
    }

    public void removeEmergency(String payloadId) {
        LOGGER.info(String.format("BEFORE REMOVE, size: %s", emergencyEquippedPolicemanList.size()));
        emergencyEquippedPolicemanList
                .removeIf(emergency -> emergency.getPayloadId().equals(payloadId));
        LOGGER.info(String.format("REMOVE, size: %s", emergencyEquippedPolicemanList.size()));
    }
}
