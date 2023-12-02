package com.police.vehicles;

import com.police.vehicles.data.EmergencyVehicles;
import jakarta.annotation.PostConstruct;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.police.vehicles.data.Vehicle;
import com.police.vehicles.data.Type;

@Component
public class VehicleRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleRepository.class);
    private static List<EmergencyVehicles> emergencyVehiclesList = new ArrayList<>();
    private static final Map<String, Vehicle> vehicles = new HashMap<>();
    private static final List<Vehicle> vehiclesStandard1 = new ArrayList<>();
    private static final List<Vehicle> vehiclesBeating = new ArrayList<>();
    private static final List<Vehicle> vehiclesChase = new ArrayList<>();
    private static final List<Vehicle> vehiclesPrisoner = new ArrayList<>();
    private static final List<Vehicle> vehiclesHighRisk = new ArrayList<>();

    @PostConstruct
    public void initData() {
        Vehicle standard1 = new Vehicle("Kia Sportage", Type.STANDARD, 4);
        vehicles.put(standard1.getName(), standard1);

        Vehicle standard2 = new Vehicle("Opel Astra", Type.STANDARD, 4);
        vehicles.put(standard2.getName(), standard2);

        Vehicle chase1 = new Vehicle("BMW 330i", Type.CHASE, 4);
        vehicles.put(chase1.getName(), chase1);

        Vehicle chase2 = new Vehicle("BMW X5", Type.CHASE, 4);
        vehicles.put(chase2.getName(), chase2);

        Vehicle motorcycle1 = new Vehicle("BMW R 1250 RT", Type.MOTORCYCLE, 1);
        vehicles.put(motorcycle1.getName(), motorcycle1);
        Vehicle motorcycle2 = new Vehicle("Kawasaki Versys 1000", Type.MOTORCYCLE, 1);
        vehicles.put(motorcycle2.getName(), motorcycle2);

        Vehicle prisoner1 = new Vehicle("Fiat Ducato", Type.PRISONER, 8);
        vehicles.put(prisoner1.getName(), prisoner1);
        Vehicle prisoner2 = new Vehicle("MAN TGE", Type.PRISONER, 6);
        vehicles.put(prisoner2.getName(), prisoner1);

        vehiclesStandard1.addAll(List.of(standard1, standard2));
        vehiclesBeating.addAll(List.of(standard1, standard2, motorcycle1));
        vehiclesChase.addAll(List.of(chase1, chase2, motorcycle2));
        vehiclesPrisoner.addAll(List.of(prisoner1, prisoner2));
        vehiclesHighRisk.addAll(List.of(chase1, chase2, motorcycle1, motorcycle2, prisoner2, standard1, standard2));
    }

    public Vehicle findVehicle(String name) {
        Assert.notNull(name, "The vehicle's name must not be null");
        return vehicles.get(name);
    }

    public List<Vehicle> findVehicles(String name) {
        return switch (name) {
            case "standard" -> vehiclesStandard1;
            case "beating" -> vehiclesBeating;
            case "chase" -> vehiclesChase;
            case "prisoner" -> vehiclesPrisoner;
            // vehiclesHighRisk
            case "high risk" -> Collections.emptyList();
            default -> {
                Assert.notNull(name, "There is no vehicles list of this type");
                yield Collections.emptyList();
            }
        };
    }

    public void saveEmergency(EmergencyVehicles emergencyVehicles) {
        emergencyVehiclesList.add(emergencyVehicles);
        LOGGER.info(String.format("ADD, size: %s", emergencyVehiclesList.size()));
    }

    public void removeEmergency(String payloadId) {
        LOGGER.info(String.format("BEFORE REMOVE, size: %s", emergencyVehiclesList.size()));
        emergencyVehiclesList.removeIf(emergency -> emergency.getPayloadId().equals(payloadId));
        LOGGER.info(String.format("REMOVE, size: %s", emergencyVehiclesList.size()));
    }
}