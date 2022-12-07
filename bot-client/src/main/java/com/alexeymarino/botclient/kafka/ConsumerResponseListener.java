package com.alexeymarino.botclient.kafka;


import com.alexeymarino.botclient.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerResponseListener {

	private final TelegramBot telegramBot;

    @KafkaListener(topics = "${topic.responseSM}", properties = {"value.deserializer=${send-message-deserializer}"})
    public void executeSendMessage(ConsumerRecord<String, SendMessage> response) {
        log.info("ResponseListener. Response: key - {}, value - {}", response.key(), response.value());
        try {
            telegramBot.execute(response.value());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "${topic.responseSM}", properties = {"value.deserializer=${send-photo-deserializer}"})
    public void executeSendPhoto(ConsumerRecord<String, SendPhoto> response) {
        log.info("ResponseListener. Response: key - {}, value - {}", response.key(), response.value());
        try {
            telegramBot.execute(response.value());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "${topic.responseDM}", properties = {"value.deserializer=${delete-message-deserializer}"})
    public void executeDeleteMessage(ConsumerRecord<String, DeleteMessage> response) {
        log.info("ResponseListener. Response: key - {}, value - {}", response.key(), response.value());
        try {
            telegramBot.execute(response.value());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "${topic.responseCBA}", properties = {"value.deserializer=${c-b-answer-message-deserializer}"})
    public void executeAnswerCallbackQuery(ConsumerRecord<String, AnswerCallbackQuery> response) {
        log.info("ResponseListener. Response: key - {}, value - {}", response.key(), response.value());
        try {
            telegramBot.execute(response.value());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
