package com.police.emergencies.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.basedomains.EquippedPoliceman;
import com.police.basedomains.EventType;
import com.police.basedomains.StandardPayload;
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

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String stringPayload, @Header("EVENT_TYPE") String eventType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StandardPayload payload = objectMapper.readValue(stringPayload, StandardPayload.class);

        if (eventType.equals(EventType.POLICEMEN_READY.toString())) {
            LOGGER.info(String.format("Event of type %s received in emergencies service => %s ", eventType, payload.toString()));

            String payloadId = payload.getPayloadId();
            List<EquippedPoliceman> equippedPolicemen = objectMapper.readValue(payload.getPayload(), new TypeReference<>() {});
            // save policemen
            // check if vehicles with this payloadId exist
            // 1. if exist -> send EMERGENCY_COMPLETED
            // 2. if doesnt exist -> do nothing, wait for VEHICLES READY with payloadId
        } else if (eventType.equals(EventType.POLICEMEN_ERROR.toString())) {
            LOGGER.info(String.format("Event of type %s received in emergencies service => %s ", eventType, payload.toString()));
            // check if vehicles with this payloadId eixst
            // 1. if exist -> send POLICEMEN_FAILED
            // 2. if doesnt exist -> send EMERGENCY_FAILED
        } else if (eventType.equals(EventType.POLICEMEN_BACKWARD_RECOVERY.toString())) {
            LOGGER.info(String.format("Event of type %s received in emergencies service => %s ", eventType, payload.toString()));
            // delete policemen with this payloadId
            // send EMERGENCY_FAILED
        }
        else {
            LOGGER.info(String.format("Event received in policemen service => %s ", payload));
        }
    }
}
