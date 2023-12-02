package com.police.vehicles.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.vehicles.VehiclesService;
import com.police.vehicles.baseDomains.EventType;
import com.police.vehicles.data.EmergencyVehicles;
import com.police.vehicles.data.GetVehicleListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.police.vehicles.baseDomains.StandardPayload;

@Service
public class EmergencyConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyConsumer.class);
    private final VehiclesService vehiclesService;

    public EmergencyConsumer(VehiclesService vehiclesService) {
        this.vehiclesService = vehiclesService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String stringPayload, @Header("EVENT_TYPE") String eventType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StandardPayload payload = objectMapper.readValue(stringPayload, StandardPayload.class);

        LOGGER.info(String.format("Event of type %s received in vehicles service => %s ", eventType, payload.toString()));
        String payloadId = payload.getPayloadId();

        if (eventType.equals(EventType.EMERGENCY_ACCEPTED.toString())) {
            GetVehicleListResponse vehicleList = new GetVehicleListResponse();
            vehicleList.setVehicles(vehiclesService.getVehicles(payload.getPayload()));

            if (vehicleList.getVehicles().isEmpty()) {
                LOGGER.info("VEHICLES: SOMETHING WENT WRONG");
                vehiclesService.sendErrorMessage(payloadId);
            } else {
                vehiclesService.addEmergency(new EmergencyVehicles(payloadId, vehicleList));
                vehiclesService.sendReadyMessage(vehicleList, payloadId);
            }
        } else if (eventType.equals(EventType.POLICEMEN_FAILED.toString())) {
            vehiclesService.removeEmergency(payloadId);
        }
    }
}
