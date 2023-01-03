package com.alexeymarino.botserver.kafka;

import com.alexeymarino.botcore.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, ResponseMessage> kafkaTemplate;

    @Value("${topic.response}")
    private String topic;

    public void produce(ResponseMessage responseMessage) {
        log.info("The produce got ResponseMessage: {}", responseMessage);
        kafkaTemplate.send(topic, responseMessage);
        kafkaTemplate.flush();
    }
}

