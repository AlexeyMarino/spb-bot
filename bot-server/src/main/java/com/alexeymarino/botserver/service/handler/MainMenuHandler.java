package com.alexeymarino.botserver.service.handler;

import com.alexeymarino.botcore.model.ResponseMessage;
import com.alexeymarino.botserver.service.support.BotMethodService;
import com.alexeymarino.botserver.service.support.KeyboardService;
import com.alexeymarino.botserver.service.support.TextMessagesService;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import static com.alexeymarino.botserver.model.MenuType.NEWS;
import static com.alexeymarino.botserver.model.MenuType.RESULT;
import static com.alexeymarino.botserver.model.MenuType.SCHEDULE;
import static com.alexeymarino.botserver.util.Constants.SELECTED_MENU;

@Service
public class MainMenuHandler extends AbstractInputMessageHandler {

    public MainMenuHandler(KeyboardService keyboardService, BotMethodService botMethodService, TextMessagesService messagesService, StateMachinePersister<States, Events, Long> persister) {
        super(keyboardService, botMethodService, messagesService, persister);
    }

    @Override
    public ResponseMessage handleInputMessage(Message message, StateMachine<States, Events> stateMachine) {
        if (message.getText().equals(messagesService.getText("button.news"))) {
            saveStateMachine(stateMachine, SELECTED_MENU, NEWS, message, Events.OPEN_SPORT_SELECTION);
            return getResponseMessage(message, messagesService.getText("text.sport_selection"), keyboardService.getSportMenu());
        } else if (message.getText().equals(messagesService.getText("button.schedule"))) {
            saveStateMachine(stateMachine, SELECTED_MENU, SCHEDULE, message, Events.OPEN_SPORT_SELECTION);
            return getResponseMessage(message, messagesService.getText("text.sport_selection"), keyboardService.getSportMenu());
        } else if (message.getText().equals(messagesService.getText("button.result"))) {
            saveStateMachine(stateMachine, SELECTED_MENU, RESULT, message, Events.OPEN_SPORT_SELECTION);
            return getResponseMessage(message, messagesService.getText("text.sport_selection"), keyboardService.getSportMenu());
        } else if (message.getText().equals(messagesService.getText("button.feedback"))) {
            return getResponseMessage(message, messagesService.getText("text.feedback"), null);
        }
        return null;
    }

    @Override
    public States getState() {
        return States.MAIN_MENU;
    }

}
