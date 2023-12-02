package com.police.vehicles.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.police.vehicles.baseDomains.Event;
import com.police.vehicles.baseDomains.StandardPayload;

@Service
public class EmergencyProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyProducer.class);
    private NewTopic topic;
    private KafkaTemplate<String, String> kafkaTemplate;

    public EmergencyProducer(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public NewTopic getTopic() {
        return topic;
    }

    public void setTopic(NewTopic topic) {
        this.topic = topic;
    }

    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Event event) throws JsonProcessingException {
        String eventType = event.getEventType().name();
        StandardPayload payload = new StandardPayload(event.getPayloadId(), event.getPayload());

        LOGGER.info(String.format("Sending event of type %s to vehicles topic => %s ", eventType, payload.toString()));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);

        Message<String> message = MessageBuilder
                .withPayload(jsonPayload)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .setHeader("EVENT_TYPE", eventType)
                .build();

        kafkaTemplate.send(message);
    }

}
