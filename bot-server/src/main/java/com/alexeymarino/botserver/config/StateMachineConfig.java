package com.alexeymarino.botserver.config;

import com.alexeymarino.botserver.model.MenuType;
import com.alexeymarino.botserver.model.ScrollableListWrapper;
import com.alexeymarino.botserver.service.ParserService;
import com.alexeymarino.botserver.statemachine.Events;
import com.alexeymarino.botserver.statemachine.States;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import static com.alexeymarino.botserver.util.Constants.SELECTED_MENU;
import static com.alexeymarino.botserver.util.Constants.SELECTED_SPORT;


@Configuration
@Slf4j
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final ParserService parserService;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.START)
                .state(States.MAIN_MENU)
                .state(States.SPORT_MENU)
                .state(States.VIEWING);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(States.START)
                .target(States.MAIN_MENU)
                .event(Events.OPEN_MAIN_MENU)
                .and()
                .withExternal()
                .source(States.MAIN_MENU)
                .target(States.SPORT_MENU)
                .event(Events.OPEN_SPORT_SELECTION)
                .and()
                .withExternal()
                .source(States.SPORT_MENU)
                .target(States.VIEWING)
                .event(Events.OPEN_VIEWING)
                .action(fetchContentAction())
                .and()
                .withExternal()
                .source(States.SPORT_MENU).source(States.VIEWING)
                .target(States.MAIN_MENU)
                .event(Events.RETURN_MAIN_MENU)
                .action(clearContextVariables());
    }

    private Action<States, Events> clearContextVariables() {
        return context -> context.getExtendedState().getVariables().clear();
    }

    private Action<States, Events> fetchContentAction() {
        return context -> {
            Map<Object, Object> variables = context.getStateMachine()
                    .getExtendedState()
                    .getVariables();
            MenuType menuName = (MenuType) variables.get(SELECTED_MENU);
            MenuType sportName = (MenuType) variables.get(SELECTED_SPORT);
            log.debug("Received parameters: Type - {}, SubType - {}", menuName, sportName);
            variables.put("CONTENT", new ScrollableListWrapper(parserService.getContentList(menuName, sportName)));
        };
    }
}
