package com.alexeymarino.botcore.model;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

@Setter
@Getter
public class ResponseMessage {
    private SendMessage sendMessage;
    private SendPhoto sendPhoto;
    private AnswerCallbackQuery callbackQuery;
    private DeleteMessage deleteMessage;
}
