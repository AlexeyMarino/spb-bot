package com.alexeymarino.botserver.service.parser;

import com.alexeymarino.botserver.model.MenuType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Parser {
    List<String> getContent();

    MenuType getParserType();
    MenuType getParserSubType();

    default List<String> convertToContentList(List<String> strings, int pageSize) {
        int size = strings.size();
        if(size < pageSize) {
            return strings;
        }

        return IntStream.range(0, size / pageSize)
                .mapToObj(i -> String.join("\n", strings.subList(i * pageSize, (i + 1) * pageSize)))
                .collect(Collectors.toList());
    }
}
