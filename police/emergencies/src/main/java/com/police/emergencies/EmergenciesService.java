package com.police.emergencies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.police.basedomains.Event;
import com.police.basedomains.EventType;
import com.police.basedomains.policemen.EquippedPoliceman;
import com.police.basedomains.vehicles.GetVehicleListResponse;
import com.police.emergencies.data.Emergency;
import com.police.emergencies.data.FullDataEmergency;
import com.police.emergencies.data.Type;
import com.police.emergencies.kafka.EmergencyProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergenciesService {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmergenciesService.class);
    private final EmergencyProducer emergencyProducer;
    private final FullDataEmergency fullDataEmergency;

    public EmergenciesService(EmergencyProducer emergencyProducer, FullDataEmergency fullDataEmergency) {
        this.emergencyProducer = emergencyProducer;
        this.fullDataEmergency = fullDataEmergency;
    }

    public String getEmergencyResult(Emergency emergency) {
        Type type = emergency.getType();

        return decideEmergencyResult(type);
    }

    private String decideEmergencyResult(Type type) {
        if (type == Type.BAD_PARKING || type == Type.DISTURBANCE_OF_ORDER) {
            return "standard";
        }

        if (type == Type.RECKLESS_DRIVING) {
            return "chase";
        }

        if (type == Type.THEFT) {
            return "prisoner";
        }

        if (type == Type.BEATING) {
            return "beating";
        }

        if (type == Type.SHOOTING || type == Type.TERRORISM) {
            return "high risk";
        }

        return "standard";
    }

    public void savePolicemen(List<EquippedPoliceman> equippedPolicemen, String payloadId) throws JsonProcessingException {
        // vehicles already in hashMap
        if (fullDataEmergency.checkIfExist(payloadId)) {
            LOGGER.info(String.format("2/2 add policeman, vehicles ready, payloadId: %s, size: %s", payloadId, fullDataEmergency.getData().size()));
            fullDataEmergency.getData().get(payloadId).setEquippedPolicemen(equippedPolicemen);
            sendCompletedMessage(payloadId);
        } else {
            LOGGER.info(String.format("1/2 add policeman, wait for vehicles, payloadId: %s, size: %s", payloadId, fullDataEmergency.getData().size()));
            fullDataEmergency.setData(payloadId, equippedPolicemen);
        }
    }

    public void saveVehicles(GetVehicleListResponse vehicleList, String payloadId) throws JsonProcessingException {
        // policemen already in hashMap
        if (fullDataEmergency.checkIfExist(payloadId)) {
            LOGGER.info(String.format("2/2 add vehicles, policemen ready, payloadId: %s, size: %s", payloadId, fullDataEmergency.getData().size()));
            fullDataEmergency.getData().get(payloadId).setVehicles(vehicleList);
            sendCompletedMessage(payloadId);
        } else {
            LOGGER.info(String.format("1/2 add vehicles, wait for policeman, payloadId: %s, size: %s", payloadId, fullDataEmergency.getData().size()));
            fullDataEmergency.setData(payloadId, vehicleList);
        }
    }

    public void deleteData(String payloadId) throws JsonProcessingException {
        if (fullDataEmergency.checkIfExist(payloadId)) {
            LOGGER.info(String.format("deleting data, payloadId: %s", payloadId));
            fullDataEmergency.getData().remove(payloadId);
        }

        sendFailedMessage(payloadId);
    }

    public void checkVehicles(String payloadId) throws JsonProcessingException {
        sendPolicemenFailedMessage(payloadId);
    }

    public void checkPolicemen(String payloadId) throws JsonProcessingException {
        sendVehiclesFailedMessage(payloadId);
    }

    public void sendCompletedMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.EMERGENCY_COMPLETED, "Vehicles and policemen ready", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    private void sendPolicemenFailedMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.POLICEMEN_FAILED, "Policemen failed, start backward recovery for vehicles", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    private void sendVehiclesFailedMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.VEHICLES_FAILED, "Vehicles failed, start backward recovery for policemen", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    private void sendFailedMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.EMERGENCY_FAILED, "Emergency failed, please try again", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }
}
