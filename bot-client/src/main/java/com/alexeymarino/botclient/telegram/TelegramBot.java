package com.alexeymarino.botclient.telegram;

import com.alexeymarino.botclient.kafka.ProducerRequestService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(TelegramLongPollingBot.class);
    private ProducerRequestService producerService;

    @Value("${telegram.bot-name}")
    private String botUsername;
    @Value("${telegram.bot-token}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
        producerService.produce(update);
    }
}
