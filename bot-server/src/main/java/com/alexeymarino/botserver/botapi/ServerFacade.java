package com.alexeymarino.botserver.botapi;

import com.alexeymarino.botserver.cache.DataCache;
import com.alexeymarino.botserver.domain.User;
import com.alexeymarino.botserver.entity.UserEntity;
import com.alexeymarino.botserver.kafka.ProducerClientService;
import com.alexeymarino.botserver.repository.UserRepository;
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

@RequiredArgsConstructor
@Component
@Slf4j
public class ServerFacade {
    private final BotStateContext botStateContext;
    private final DataCache userDataCache;
    private final KeyboardService keyboardService;
    private final UserRepository userRepository;

    private final ProducerClientService producerClientService;

    public void handleUpdate(Update update) {
        List<PartialBotApiMethod<?>> replyMessage = new ArrayList<>();

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            saveNewUser(callbackQuery.getFrom().getId(), callbackQuery.getFrom().getUserName());
            replyMessage = handleInputCallBackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                saveNewUser(message.getChatId(), message.getFrom().getUserName());
                replyMessage = handleInputMessage(message);
            }
        }
        producerClientService.produce(replyMessage);
    }

    private void saveNewUser(Long chatId, String name) {
        if (!userDataCache.isUserContain(chatId)) {
            User user = new User(chatId, name);
            userRepository.save(new UserEntity(user));
            userDataCache.add(user);
        }
    }

    private List<PartialBotApiMethod<?>> handleInputMessage(Message message) {
        log.info("New message from User:{}, chatId: {},  with text: {}",
                message.getFrom().getUserName(), message.getChatId(), message.getText());

        String inputMsg = message.getText();
        long chatId = message.getChatId();
        BotState botState = userDataCache.getUsersCurrentBotState(chatId);

        if (inputMsg.equals("/start")) {
            botState = BotState.START;
            userDataCache.setUsersCurrentBotState(chatId, botState);
            return Collections.singletonList(keyboardService.setMenu(chatId));
        }
        return botStateContext.processInputMessage(botState, message);
    }

    private List<PartialBotApiMethod<?>> handleInputCallBackQuery(CallbackQuery callbackQuery) {
        log.info("New callbackQuery from User: {}, userId: {}, with data: {}", callbackQuery.getFrom().getUserName(),
                callbackQuery.getFrom().getId(), callbackQuery.getData());
        return botStateContext.processInputCallBack(userDataCache.getUsersCurrentBotState(callbackQuery.getFrom().getId()), callbackQuery);
    }


}
