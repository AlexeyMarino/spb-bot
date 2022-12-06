package com.alexeymarino.botserver.kafka;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerClientService {
    private final KafkaTemplate<String, SendMessage> kafkaSendMessageTemplate;
    private final KafkaTemplate<String, SendPhoto> kafkaSendPhotoTemplate;
    private final KafkaTemplate<String, SendMessage> AnswerCallbackQuery;

    @Value("${topic.response}")
    private String topicSendMessageResponse;

    public void produce(List<PartialBotApiMethod<?>> replyMessage) {
        log.info("The produce got replyMessage: {}", replyMessage);
        for (PartialBotApiMethod<?> method : replyMessage) {
            if (method instanceof SendPhoto) {
                //kafkaSendPhotoTemplate.send(TOPIC(SendPhoto) method);
            } else if (method instanceof SendMessage) {
                kafkaSendMessageTemplate.send(topicSendMessageResponse, (SendMessage) method);
                kafkaSendMessageTemplate.flush();
            } else if (method instanceof AnswerCallbackQuery) {
                //to do
            }
        }
    }
}
