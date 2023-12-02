package com.police.vehicles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.vehicles.baseDomains.Event;
import com.police.vehicles.baseDomains.EventType;
import com.police.vehicles.data.EmergencyVehicles;
import com.police.vehicles.data.GetVehicleListResponse;
import com.police.vehicles.data.Vehicle;
import com.police.vehicles.kafka.EmergencyProducer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiclesService {

    private final VehicleRepository vehicleRepository;
    private final EmergencyProducer emergencyProducer;

    public VehiclesService(VehicleRepository vehicleRepository, EmergencyProducer emergencyProducer) {
        this.vehicleRepository = vehicleRepository;
        this.emergencyProducer = emergencyProducer;
    }

    public Vehicle getVehicle(String name) {
        return vehicleRepository.findVehicle(name);
    }

    public List<Vehicle> getVehicles(String name) {
        return vehicleRepository.findVehicles(name);
    }

    public void addEmergency(EmergencyVehicles emergencyVehicles) {
        vehicleRepository.saveEmergency(emergencyVehicles);
    }

    public void removeEmergency(String payloadId) throws JsonProcessingException {
        vehicleRepository.removeEmergency(payloadId);
        sendVehiclesBackwardRecoveryMessage(payloadId);
    }

    public void sendReadyMessage(GetVehicleListResponse vehicleList, String payloadId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(vehicleList);
        Event newEvent = new Event(EventType.VEHICLES_READY, jsonPayload, payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    public void sendErrorMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.VEHICLES_ERROR, "Error", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }

    private void sendVehiclesBackwardRecoveryMessage(String payloadId) throws JsonProcessingException {
        Event newEvent = new Event(EventType.VEHICLES_BACKWARD_RECOVERY, "Policemen Backward recovery", payloadId);
        emergencyProducer.sendMessage(newEvent);
    }
}
