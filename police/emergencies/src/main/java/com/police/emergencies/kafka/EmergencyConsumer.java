package com.police.emergencies.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.basedomains.policemen.EquippedPoliceman;
import com.police.basedomains.EventType;
import com.police.basedomains.StandardPayload;
import com.police.basedomains.vehicles.GetVehicleListResponse;
import com.police.basedomains.vehicles.Vehicle;
import com.police.emergencies.EmergenciesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyConsumer.class);
    private final EmergenciesService emergenciesService;

    public EmergencyConsumer(EmergenciesService emergenciesService) {
        this.emergenciesService = emergenciesService;
    }

    @KafkaListener(topics = "${kafka.topic.name.policemen}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePolicemen(String stringPayload, @Header("EVENT_TYPE") String eventType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StandardPayload payload = objectMapper.readValue(stringPayload, StandardPayload.class);

        LOGGER.info(String.format("Event of type %s received in emergencies service => %s ", eventType, payload.toString()));
        String payloadId = payload.getPayloadId();

        if (eventType.equals(EventType.POLICEMEN_READY.toString())) {
            List<EquippedPoliceman> equippedPolicemen = objectMapper.readValue(payload.getPayload(), new TypeReference<>() {});
            emergenciesService.savePolicemen(equippedPolicemen, payloadId);
        } else if (eventType.equals(EventType.POLICEMEN_ERROR.toString())) {
            emergenciesService.checkVehicles(payloadId);
        } else if (eventType.equals(EventType.POLICEMEN_BACKWARD_RECOVERY.toString())) {
            emergenciesService.deleteData(payloadId);
        }
    }

    @KafkaListener(topics = "${kafka.topic.name.vehicles}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeVehicles(String stringPayload, @Header("EVENT_TYPE") String eventType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StandardPayload payload = objectMapper.readValue(stringPayload, StandardPayload.class);

        LOGGER.info(String.format("Event of type %s received in emergencies service => %s ", eventType, payload.toString()));
        String payloadId = payload.getPayloadId();

        if (eventType.equals(EventType.VEHICLES_READY.toString())) {
            GetVehicleListResponse vehicleList = objectMapper.readValue(payload.getPayload(), GetVehicleListResponse.class);
            emergenciesService.saveVehicles(vehicleList, payloadId);
        } else if (eventType.equals(EventType.VEHICLES_ERROR.toString())) {
            emergenciesService.checkPolicemen(payloadId);
        } else if (eventType.equals(EventType.VEHICLES_BACKWARD_RECOVERY.toString())) {
            emergenciesService.deleteData(payloadId);
        }
    }
}
