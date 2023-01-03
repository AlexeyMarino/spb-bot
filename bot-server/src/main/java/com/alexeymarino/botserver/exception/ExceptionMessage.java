package com.alexeymarino.botserver.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {
    public static final String CONTENT_PARSE_ERROR = "Ошибка загрузки данных с сайта, попробуйте позже";
    public static final String PARSE_CONNECT_ERROR = "Ошибка при подключении к сайту, попробуйте позже";
    public static final String PERSIST_ERROR = "Ошибка при получении statemachine из контекста";
    public static final String RESTORE_ERROR = "Ошибка при сохранении statemachine в контекст";
}
