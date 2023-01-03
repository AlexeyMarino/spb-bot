package com.alexeymarino.botserver.config;

import com.alexeymarino.botserver.model.MenuType;
import com.alexeymarino.botserver.service.parser.Parser;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {

    @Bean
    public Map<MenuType, Map<MenuType, Parser>> parsersMap(Set<Parser> parsers) {
        return parsers.stream()
                .collect(Collectors.groupingBy(Parser::getParserType, Collectors.toMap(Parser::getParserSubType, Function.identity())));
    }

}
