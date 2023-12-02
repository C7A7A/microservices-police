package com.police.policemen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.basedomains.*;
import com.police.basedomains.policemen.*;
import com.police.policemen.data.*;
import com.police.policemen.kafka.EmergencyProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicemenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolicemenService.class);

    private final PolicemenRepository policemenRepository;
    private final EmergencyProducer emergencyProducer;

    public PolicemenService(PolicemenRepository policemenRepository, EmergencyProducer emergencyProducer) {
        this.policemenRepository = policemenRepository;
        this.emergencyProducer = emergencyProducer;
    }

    public List<Policeman> getPolicemen() {
        return policemenRepository.generatePolicemen();
    }

    public List<Weapon> getWeapons() {
        return policemenRepository.generateWeapons();
    }

    public List<EquippedPoliceman> preparePolicemen(String threat) {
        List<Policeman> policemen = policemenRepository.generatePolicemen();
        List<Weapon> weapons = policemenRepository.generateWeapons();

        return switch (threat) {
            case "standard" -> equipPolicemen(policemen, weapons, DangerLevel.LOW, Rank.SENIOR_SERGEANT, 0.8);
            // equipPolicemen(policemen, weapons, DangerLevel.LOW, Rank.STAFF_SERGEANT, 0.7);
            case "beating" -> Collections.emptyList(); // Simulate that this doesn't work
            case "chase" -> equipPolicemen(policemen, weapons, DangerLevel.HIGH, Rank.SENIOR_ASPIRANT, 0.5);
            case "prisoner" -> equipPolicemen(policemen, weapons, DangerLevel.MEDIUM, Rank.ASPIRANT, 0.6);
            case "high risk" -> equipPolicemen(policemen, weapons, DangerLevel.HIGH, Rank.STAFF_ASPIRANT, 0.65);
            default -> {
                Assert.notNull(threat, "Something went wrong. There is no threat of this type!");
                yield Collections.emptyList();
            }
        };
    }

    private List<EquippedPoliceman> equipPolicemen(
            List<Policeman> policemen, List<Weapon> weapons, DangerLevel level, Rank maxRank, double howMany
    ) {
        List<Weapon> chosenWeapons = weapons.stream()
                .filter(weapon -> weapon.getDangerLevel().equals(level))
                .toList();

        List<Policeman> chosenPolicemen = policemen.stream()
                .filter(policeman -> policeman.getRank().getLevel() <= maxRank.getLevel())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        listToShuffle -> {
                            Collections.shuffle(listToShuffle);
                            int limit = (int) (listToShuffle.size() * howMany);
                            return listToShuffle.subList(0, limit);
                        }
                ));

        return chosenPolicemen.stream()
                    .map(policeman -> new EquippedPoliceman(policeman, chosenWeapons))
                    .toList();

    }

    public void addEmergency(EmergencyEquippedPoliceman emergencyEquippedPoliceman) {
        policemenRepository.saveEmergency(emergencyEquippedPoliceman);
    }

    public void removeEmergency(String payloadId) throws JsonProcessingException {
        policemenRepository.removeEmergency(payloadId);
        sendPolicemenBackwardRecoveryMessage(payloadId);
    }

    public void sendReadyMessage(List<EquippedPoliceman> equippedPolicemen, String payloadId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(equippedPolicemen);
        Event newEvent = new Event(EventType.POLICEMEN_READY, jsonPayload, payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    public void sendErrorMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.POLICEMEN_ERROR, "Error", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    private void sendPolicemenBackwardRecoveryMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.POLICEMEN_BACKWARD_RECOVERY, "Policemen Backward recovery", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }
}
