package com.alexeymarino.botserver.service.handler;

import com.alexeymarino.botcore.model.ResponseMessage;
import com.alexeymarino.botserver.exception.StateMachinePersistException;
import com.alexeymarino.botserver.model.MenuType;
import com.alexeymarino.botserver.service.support.BotMethodService;
import com.alexeymarino.botserver.service.support.KeyboardService;
import com.alexeymarino.botserver.service.support.TextMessagesService;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import static com.alexeymarino.botserver.exception.ExceptionMessage.PERSIST_ERROR;

@RequiredArgsConstructor
@Slf4j
@Service
public abstract class AbstractInputMessageHandler implements InputMessageHandler {

    protected final KeyboardService keyboardService;
    protected final BotMethodService botMethodService;
    protected final TextMessagesService messagesService;
    protected final StateMachinePersister<States, Events, Long> persister;

    protected void saveStateMachine(StateMachine<States, Events> stateMachine, String selectedItem,
                                    MenuType selectedType, Message message, Events event) {
        stateMachine.getExtendedState().getVariables().put(selectedItem, selectedType);
        stateMachine.sendEvent(event);
        try {
            persister.persist(stateMachine, message.getChatId());
        } catch (Exception e) {
            log.error(PERSIST_ERROR);
            throw new StateMachinePersistException(PERSIST_ERROR);
        }
    }

    protected ResponseMessage getResponseMessage(Message message, String text, ReplyKeyboardMarkup keyboard) {
        ResponseMessage responseMessage = new ResponseMessage();
        SendMessage sendMessage = botMethodService.getSendMessage(
                message.getChatId(),
                text,
                keyboard);
        responseMessage.setSendMessage(sendMessage);
        return responseMessage;
    }

    protected void returnMainMenuStateMachine(StateMachine<States, Events> stateMachine, Message message) {
        stateMachine.sendEvent(Events.RETURN_MAIN_MENU);
        try {
            persister.persist(stateMachine, message.getChatId());
        } catch (Exception e) {
            log.error(PERSIST_ERROR);
            throw new StateMachinePersistException(PERSIST_ERROR);
        }
    }

    @Override
    public ResponseMessage handleInputCallBackQuery(CallbackQuery callbackQuery, StateMachine<States, Events> stateMachine) {
        return null;
    }
}
