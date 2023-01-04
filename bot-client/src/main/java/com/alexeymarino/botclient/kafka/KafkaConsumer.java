package com.alexeymarino.botclient.kafka;


import com.alexeymarino.botclient.telegram.TelegramBot;
import com.alexeymarino.botcore.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TelegramBot telegramBot;

    @KafkaListener(topics = "${topic.response}")
    public void executeResponseMessage(ConsumerRecord<String, ResponseMessage> response) {
        log.info("ResponseMessageListener. Response: {}", response.value());
        telegramBot.send(response.value());
    }
}
