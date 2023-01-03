package com.alexeymarino.botclient.telegram;

import com.alexeymarino.botclient.kafka.KafkaProducer;
import com.alexeymarino.botclient.util.CheckedConsumer;
import com.alexeymarino.botcore.model.ResponseMessage;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final KafkaProducer producerService;

    @Value("${telegram.bot-name}")
    private String botUsername;
    @Value("${telegram.bot-token}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
        producerService.produce(update);
    }

    public void send(ResponseMessage message) {
        Optional.ofNullable(message.getSendMessage()).ifPresent(m -> safeExecute(this::execute, m));
        Optional.ofNullable(message.getSendPhoto()).ifPresent(m -> safeExecute(this::execute, m));
        Optional.ofNullable(message.getCallbackQuery()).ifPresent(m -> safeExecute(this::execute, m));
        Optional.ofNullable(message.getDeleteMessage()).ifPresent(m -> safeExecute(this::execute, m));
    }

    @SuppressWarnings({"rawtypes"})
    private <T extends PartialBotApiMethod> void safeExecute(CheckedConsumer<T> executeConsumer, T message) {
        try {
            executeConsumer.accept(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
