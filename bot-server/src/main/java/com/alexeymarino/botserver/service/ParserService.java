package com.alexeymarino.botserver.service;

import com.alexeymarino.botserver.model.MenuType;
import com.alexeymarino.botserver.service.parser.Parser;
import com.alexeymarino.botserver.service.parser.Parsers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserService {
    private final Map<MenuType, Map<MenuType, Parser>> contentParsers;

    public List<String> getContentList(MenuType menuType, MenuType menuSubType) {
        Parser parser = getParser(menuType, menuSubType);
        return parser.getContent();
    }

    private Parser getParser(MenuType type, MenuType subType) {
        return contentParsers.get(type).get(subType);
    }

}