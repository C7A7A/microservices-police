package com.police.policemen.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.basedomains.EventType;
import com.police.basedomains.StandardPayload;
import com.police.policemen.PolicemenService;
import com.police.policemen.data.EmergencyEquippedPoliceman;
import com.police.basedomains.policemen.EquippedPoliceman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyConsumer.class);
    private final PolicemenService policemenService;

    public EmergencyConsumer(PolicemenService policemenService) {
        this.policemenService = policemenService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String stringPayload, @Header("EVENT_TYPE") String eventType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StandardPayload payload = objectMapper.readValue(stringPayload, StandardPayload.class);

        LOGGER.info(String.format("Event of type %s received in policemen service => %s ", eventType, payload.toString()));
        String payloadId = payload.getPayloadId();

        if (eventType.equals(EventType.EMERGENCY_ACCEPTED.toString())) {
            List<EquippedPoliceman> equippedPolicemen = policemenService.preparePolicemen(payload.getPayload());

            if (equippedPolicemen.isEmpty()) {
                LOGGER.info("POLICEMEN: SOMETHING WENT WRONG");
                policemenService.sendErrorMessage(payloadId);
            } else {
                policemenService.addEmergency(new EmergencyEquippedPoliceman(payloadId, equippedPolicemen));
                policemenService.sendReadyMessage(equippedPolicemen, payloadId);
            }
        } else if (eventType.equals(EventType.VEHICLES_FAILED.toString())) {
            policemenService.removeEmergency(payloadId);
        }
    }
}
