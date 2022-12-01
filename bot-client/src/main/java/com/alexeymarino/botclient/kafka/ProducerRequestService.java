package com.alexeymarino.botclient.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerRequestService {
    private final KafkaTemplate<String, Update> kafkaTemplate;

    @Value("${topic.request}")
    private String topicRequest;

    public void produce(Update update) {
        log.info("The produce got update: {}", update);
        kafkaTemplate.send(topicRequest, update.getMessage().getChatId().toString(), update);
        kafkaTemplate.flush();
        log.info("Topic posted");
    }
}
