package com.alexeymarino.botserver.service;

import com.alexeymarino.botcore.model.ResponseMessage;
import com.alexeymarino.botserver.exception.StateMachinePersistException;
import com.alexeymarino.botserver.kafka.KafkaProducer;
import com.alexeymarino.botserver.service.handler.InputMessageHandler;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import com.alexeymarino.botserver.util.CheckedFunction;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import static com.alexeymarino.botserver.exception.ExceptionMessage.PERSIST_ERROR;
import static com.alexeymarino.botserver.exception.ExceptionMessage.RESTORE_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class InputMessageService {
    private final StateMachineFactory<States, Events> stateMachineFactory;
    private final KafkaProducer kafkaProducer;
    private final StateMachinePersister<States, Events, Long> persister;
    private final Map<States, InputMessageHandler> messageHandlers;
    private final UserProfileService userProfileService;


    public void processInputUpdate(Update update) {
        kafkaProducer.produce(update.hasCallbackQuery() ?
                processCallbackQuery(update.getCallbackQuery()) :
                processMessage(update.getMessage()));
    }

    private InputMessageHandler getHandler(StateMachine<States, Events> stateMachine) {
        States currentState = stateMachine.getState().getId();
        return messageHandlers.get(currentState);
    }

    private StateMachine<States, Events> getStateMachine(Long chatId) {
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine();
        safeStateMachineAction(chatId, persister::restore, stateMachine, RESTORE_ERROR);
        safeStateMachineAction(chatId, persister::persist, stateMachine, PERSIST_ERROR);

        return stateMachine;
    }

    private <T> void safeStateMachineAction(Long chatId, CheckedFunction<T, Long> function, T stateMachine, String error) {
        try {
            function.apply(stateMachine, chatId);
        } catch (Exception e) {
            log.error(error);
            throw new StateMachinePersistException(error);
        }
    }

    private ResponseMessage processMessage(Message message) {
        StateMachine<States, Events> stateMachine = getStateMachine(message.getChatId());
        userProfileService.saveUserProfile(message.getChatId(), message.getFrom().getUserName());
        return getHandler(stateMachine).handleInputMessage(message, stateMachine);
    }

    private ResponseMessage processCallbackQuery(CallbackQuery callbackQuery) {
        StateMachine<States, Events> stateMachine = getStateMachine(callbackQuery.getMessage().getChatId());
        userProfileService.saveUserProfile(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getFrom().getUserName());
        return getHandler(stateMachine).handleInputCallBackQuery(callbackQuery, stateMachine);
    }

}
