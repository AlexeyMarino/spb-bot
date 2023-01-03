package com.alexeymarino.botserver.config;

import com.alexeymarino.botserver.service.handler.InputMessageHandler;
import com.alexeymarino.botserver.statemachine.States;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageHandlersConfig {
    @Bean
    public Map<States, InputMessageHandler> statesInputMessageHandlerMap(Set<InputMessageHandler> messageHandlers) {
        return messageHandlers.stream()
                .collect(Collectors.toMap(InputMessageHandler::getState, Function.identity()));
    }
}
