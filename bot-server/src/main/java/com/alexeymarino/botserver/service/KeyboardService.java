package com.alexeymarino.botserver.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@RequiredArgsConstructor
@Component
public class KeyboardService {

    private final TextMessagesService messagesService;

    public SendMessage setMenu() {
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();
        firstRow.add(new KeyboardButton(messagesService.getText("button.schedule")));
        firstRow.add(new KeyboardButton(messagesService.getText("button.result")));
        secondRow.add(new KeyboardButton(messagesService.getText("button.news")));
        secondRow.add(new KeyboardButton(messagesService.getText("button.feedback")));
        keyboardRowList.add(firstRow);
        keyboardRowList.add(secondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return sendMessage;
    }
}
