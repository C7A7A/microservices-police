package com.police.policemen.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.basedomains.StandardPayload;
import com.police.policemen.PolicemenService;
import com.police.policemen.data.EmergencyEquippedPoliceman;
import com.police.basedomains.EquippedPoliceman;
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

        if (eventType.equals("EMERGENCY_ACCEPTED")) {
            LOGGER.info(String.format("Event of type %s received in policemen service => %s ", eventType, payload.toString()));

            String payloadId = payload.getPayloadId();
            List<EquippedPoliceman> equippedPolicemen = policemenService.preparePolicemen(payload.getPayload());

            if (equippedPolicemen.isEmpty()) {
                LOGGER.info("POLICEMEN: SOMETHING WENT WRONG");
                // send error event
            } else {
                policemenService.addEmergency(new EmergencyEquippedPoliceman(payloadId, equippedPolicemen));
                policemenService.sendReadyMessage(equippedPolicemen, payloadId);
            }
        } else {
            LOGGER.info(String.format("Event received in policemen service => %s ", payload));
        }
    }
}
