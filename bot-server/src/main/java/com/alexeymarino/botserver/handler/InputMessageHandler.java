package com.alexeymarino.botserver.handler;

import com.alexeymarino.botserver.botapi.BotState;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {
    List<PartialBotApiMethod<?>> handle(Message message);

    BotState getHandlerName();

    List<PartialBotApiMethod<?>> handle(CallbackQuery callbackQuery);
}
