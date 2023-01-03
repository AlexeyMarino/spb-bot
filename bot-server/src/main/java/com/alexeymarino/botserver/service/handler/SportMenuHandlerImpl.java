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
import static com.alexeymarino.botserver.model.MenuType.APL;
import static com.alexeymarino.botserver.model.MenuType.DOTA;
import static com.alexeymarino.botserver.model.MenuType.RPL;
import static com.alexeymarino.botserver.util.Constants.SELECTED_SPORT;

@Service
public class SportMenuHandlerImpl extends AbstractInputMessageHandler {


    public SportMenuHandlerImpl(KeyboardService keyboardService, BotMethodService botMethodService, TextMessagesService messagesService, StateMachinePersister<States, Events, Long> persister) {
        super(keyboardService, botMethodService, messagesService, persister);
    }

    @Override
    public ResponseMessage handleInputMessage(Message message, StateMachine<States, Events> stateMachine) {
        if (message.getText().equals(messagesService.getText("button.dota"))) {
            saveStateMachine(stateMachine, SELECTED_SPORT, DOTA, message, Events.OPEN_VIEWING);
            return getResponseMessage(message, messagesService.getText("text.open_view"), keyboardService.getFirstPageViewMenu());
        } else if (message.getText().equals(messagesService.getText("button.apl"))) {
            saveStateMachine(stateMachine, SELECTED_SPORT, APL, message, Events.OPEN_VIEWING);
            return getResponseMessage(message, messagesService.getText("text.open_view"), keyboardService.getFirstPageViewMenu());
        } else if (message.getText().equals(messagesService.getText("button.rpl"))) {
            saveStateMachine(stateMachine, SELECTED_SPORT, RPL, message, Events.OPEN_VIEWING);
            return getResponseMessage(message, messagesService.getText("text.open_view"), keyboardService.getFirstPageViewMenu());
        } else if (message.getText().equals(messagesService.getText("button.return_main"))) {
            returnMainMenuStateMachine(stateMachine, message);
            return getResponseMessage(message, "", keyboardService.getMainMenu());
        }
        return null;
    }

    @Override
    public States getState() {
        return States.SPORT_MENU;
    }
}
