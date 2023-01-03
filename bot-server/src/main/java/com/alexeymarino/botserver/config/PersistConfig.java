package com.alexeymarino.botserver.config;

import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.InMemoryPersist;
import com.alexeymarino.botserver.statemachine.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
public class PersistConfig {

    @Bean
    public StateMachinePersist<States, Events, Long> inMemory() {
        return new InMemoryPersist();
    }

    @Bean
    public StateMachinePersister<States, Events, Long> persister(StateMachinePersist<States, Events, Long> defaultPersist) {
        return new DefaultStateMachinePersister<>(defaultPersist);
    }
}
