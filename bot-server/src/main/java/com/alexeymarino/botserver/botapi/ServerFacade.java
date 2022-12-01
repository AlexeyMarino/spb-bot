package com.alexeymarino.botserver.botapi;

import com.alexeymarino.botserver.cache.DataCache;
import com.alexeymarino.botserver.service.KeyboardService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

@RequiredArgsConstructor
@Component
@Slf4j
public class ServerFacade {
    private final BotStateContext botStateContext;
    private final DataCache userDataCache;
    private final KeyboardService keyboardService;

    public List<PartialBotApiMethod<?>> handleUpdate(Update update) {
        List<PartialBotApiMethod<?>> replyMessage = new ArrayList<>();
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            replyMessage = handleInputCallBackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private List<PartialBotApiMethod<?>> handleInputMessage(Message message) {
        String inputMsg = message.getText();
        long chatId = message.getChatId();
        BotState botState = userDataCache.getUsersCurrentBotState(chatId);

        if (inputMsg.equals("/start")) {
            botState = BotState.START;
            userDataCache.setUsersCurrentBotState(chatId, botState);
            return Collections.singletonList(keyboardService.setMenu());
        }
        return botStateContext.processInputMessage(botState, message);
    }

    private List<PartialBotApiMethod<?>> handleInputCallBackQuery(CallbackQuery callbackQuery) {
        return botStateContext.processInputCallBack(userDataCache.getUsersCurrentBotState(callbackQuery.getFrom().getId()), callbackQuery);
    }

}
