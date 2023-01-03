package com.alexeymarino.botserver.statemachine;

import java.util.HashMap;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

public class InMemoryPersist implements StateMachinePersist<States, Events, Long> {

    private final HashMap<Long, StateMachineContext<States, Events>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<States, Events> stateMachineContext, Long chatId) {
        contexts.put(chatId, stateMachineContext);
    }

    @Override
    public StateMachineContext<States, Events> read(Long chatId) {
        return contexts.get(chatId);
    }
}
