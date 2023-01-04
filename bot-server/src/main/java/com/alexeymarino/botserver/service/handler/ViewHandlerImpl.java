package com.alexeymarino.botserver.service.handler;

import com.alexeymarino.botcore.model.ResponseMessage;
import com.alexeymarino.botserver.model.ScrollableListWrapper;
import com.alexeymarino.botserver.service.support.BotMethodService;
import com.alexeymarino.botserver.service.support.KeyboardService;
import com.alexeymarino.botserver.service.support.TextMessagesService;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;


@Service
public class ViewHandlerImpl extends AbstractInputMessageHandler {

    public ViewHandlerImpl(KeyboardService keyboardService, BotMethodService botMethodService, TextMessagesService messagesService, StateMachinePersister<States, Events, Long> persister) {
        super(keyboardService, botMethodService, messagesService, persister);
    }

    @Override
    public ResponseMessage handleInputMessage(Message message, StateMachine<States, Events> stateMachine) {
        ScrollableListWrapper contentList = stateMachine.getExtendedState().get("CONTENT", ScrollableListWrapper.class);
        if (message.getText().equals(messagesService.getText("button.next"))) {
            if (contentList.isLast()) {
                return getResponseMessage(message, contentList.getCurrentPage(), keyboardService.getLastPageViewMenu());
            }
            return getResponseMessage(message, contentList.getNextPage(), keyboardService.getViewMenu());
        }

        if (message.getText().equals(messagesService.getText("button.prev"))) {
            if (contentList.isFirst()) {
                return getResponseMessage(message, contentList.getCurrentPage(), keyboardService.getFirstPageViewMenu());
            }
            return getResponseMessage(message, contentList.getPreviousPage(), keyboardService.getViewMenu());
        }

        if (message.getText().equals(messagesService.getText("button.menu"))) {
            returnMainMenuStateMachine(stateMachine, message);
            return getResponseMessage(message, messagesService.getText("text.menu"), keyboardService.getMainMenu());
        }
        return new ResponseMessage();
    }

    @Override
    public States getState() {
        return States.VIEWING;
    }
}
