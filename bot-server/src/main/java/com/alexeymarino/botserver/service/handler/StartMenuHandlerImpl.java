package com.alexeymarino.botserver.service.handler;

import com.alexeymarino.botcore.model.ResponseMessage;
import com.alexeymarino.botserver.exception.StateMachinePersistException;
import com.alexeymarino.botserver.service.support.BotMethodService;
import com.alexeymarino.botserver.service.support.KeyboardService;
import com.alexeymarino.botserver.service.support.TextMessagesService;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import static com.alexeymarino.botserver.exception.ExceptionMessage.PERSIST_ERROR;

@Slf4j
@Service
public class StartMenuHandlerImpl extends AbstractInputMessageHandler {

    public StartMenuHandlerImpl(KeyboardService keyboardService, BotMethodService botMethodService, TextMessagesService messagesService, StateMachinePersister<States, Events, Long> persister) {
        super(keyboardService, botMethodService, messagesService, persister);
    }

    @Override
    public ResponseMessage handleInputMessage(Message message, StateMachine<States, Events> stateMachine) {
        stateMachine.sendEvent(Events.OPEN_MAIN_MENU);
        try {
            persister.persist(stateMachine, message.getChatId());
        } catch (Exception e) {
            log.error(PERSIST_ERROR);
            throw new StateMachinePersistException(PERSIST_ERROR);
        }
        return getResponseMessage(message, messagesService.getText("text.start"), keyboardService.getMainMenu());
    }

    @Override
    public States getState() {
        return States.START;
    }

}
