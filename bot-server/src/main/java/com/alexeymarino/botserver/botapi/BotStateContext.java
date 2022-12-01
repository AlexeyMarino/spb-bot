package com.alexeymarino.botserver.botapi;

import com.alexeymarino.botserver.handler.InputMessageHandler;
import com.alexeymarino.botserver.service.TextMessagesService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class BotStateContext {

    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();
    private final TextMessagesService messagesService;

    public BotStateContext(List<InputMessageHandler> messageHandlers, TextMessagesService messagesService) {
        this.messagesService = messagesService;
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public List<PartialBotApiMethod<?>> processInputMessage(BotState currentState, Message message) {
        if (currentState == null) {
            return Collections.singletonList(new DeleteMessage(message.getChatId().toString(), message.getMessageId()));
        }
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState, message);
        if (currentMessageHandler == null) {
            return Collections.singletonList(new DeleteMessage(message.getChatId().toString(), message.getMessageId()));
        }
        log.info("User state {}, handler {}", currentState, currentMessageHandler);
        return currentMessageHandler.handle(message);
    }

    public List<PartialBotApiMethod<?>> processInputCallBack(BotState currentState, CallbackQuery callbackQuery) {
        if (currentState == null) {
            return Collections.singletonList(new DeleteMessage(callbackQuery.getMessage().getChatId().toString(), callbackQuery.getMessage().getMessageId()));
        }
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState, callbackQuery.getMessage());
        return currentMessageHandler.handle(callbackQuery);
    }



    private InputMessageHandler findMessageHandler(BotState currentState, Message message) {

        return messageHandlers.get(currentState);
    }
}
