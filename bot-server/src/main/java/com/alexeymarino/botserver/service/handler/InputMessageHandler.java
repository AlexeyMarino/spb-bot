package com.alexeymarino.botserver.service.handler;

import com.alexeymarino.botcore.model.ResponseMessage;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import org.springframework.statemachine.StateMachine;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {
    ResponseMessage handleInputMessage(Message message, StateMachine<States, Events> stateMachine);
    ResponseMessage handleInputCallBackQuery(CallbackQuery callbackQuery, StateMachine<States, Events> stateMachine);
    States getState();
}
