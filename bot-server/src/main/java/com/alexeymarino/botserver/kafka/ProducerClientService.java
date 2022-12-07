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
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerClientService {
    private final KafkaTemplate<String, SendMessage> kafkaSendMessageTemplate;
    private final KafkaTemplate<String, SendPhoto> kafkaSendPhotoTemplate;
    private final KafkaTemplate<String, AnswerCallbackQuery> kafkaAnswerCallbackQueryTemplate;
    private final KafkaTemplate<String, DeleteMessage> kafkaDeleteMessageTemplate;

    @Value("${topic.responseSM}")
    private String topicSendMessageResponse;

    @Value("${topic.responseSP}")
    private String topicSendPhotoResponse;

    @Value("${topic.responseCBA}")
    private String topicSendAnswerCallbackQueryResponse;

    @Value("${topic.responseDM}")
    private String topicDeleteMessageResponse;

    public void produce(List<PartialBotApiMethod<?>> replyMessage) {
        for (PartialBotApiMethod<?> method : replyMessage) {
            if (method instanceof SendPhoto) {
                log.info("The produce got SendPhoto: {}", replyMessage);
                kafkaSendPhotoTemplate.send(topicSendPhotoResponse, (SendPhoto) method);
                kafkaSendMessageTemplate.flush();
            } else if (method instanceof SendMessage) {
                log.info("The produce got SendMessage: {}", replyMessage);
                kafkaSendMessageTemplate.send(topicSendMessageResponse, (SendMessage) method);
                kafkaSendMessageTemplate.flush();
            } else if (method instanceof AnswerCallbackQuery) {
                log.info("The produce got AnswerCallbackQuery: {}", replyMessage);
                kafkaAnswerCallbackQueryTemplate.send(topicSendAnswerCallbackQueryResponse, (AnswerCallbackQuery) method);
                kafkaSendMessageTemplate.flush();
            } else if (method instanceof DeleteMessage) {
                log.info("The produce got DeleteMessage: {}", replyMessage);
                kafkaDeleteMessageTemplate.send(topicDeleteMessageResponse, (DeleteMessage) method);
                kafkaSendMessageTemplate.flush();
            }
        }
    }
}
