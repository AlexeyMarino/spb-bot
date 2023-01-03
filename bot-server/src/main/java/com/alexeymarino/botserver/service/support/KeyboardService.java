package com.alexeymarino.botserver.service.support;

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

    public ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
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
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getOneColumnKeyboard(Boolean oneTime, String... buttons) {
        ReplyKeyboardMarkup oneColumnKeyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        oneColumnKeyboard.setSelective(true);
        oneColumnKeyboard.setResizeKeyboard(true);
        oneColumnKeyboard.setOneTimeKeyboard(oneTime);
        for (String button : buttons) {
            KeyboardRow row = new KeyboardRow();
            row.add(button);
            keyboardRows.add(row);
        }
        oneColumnKeyboard.setKeyboard(keyboardRows);
        return oneColumnKeyboard;
    }

    private ReplyKeyboardMarkup getOneRowKeyboard(Boolean oneTime, String... buttons) {
        ReplyKeyboardMarkup oneRowKeyboard = new ReplyKeyboardMarkup();
        oneRowKeyboard.setSelective(true);
        oneRowKeyboard.setResizeKeyboard(true);
        oneRowKeyboard.setOneTimeKeyboard(oneTime);
        KeyboardRow keyboardRow = new KeyboardRow();
        for (String button : buttons) {
            keyboardRow.add(button);
        }
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);
        oneRowKeyboard.setKeyboard(keyboardRows);
        return oneRowKeyboard;
    }

    public ReplyKeyboardMarkup getSoccerMenu() {
        return getOneColumnKeyboard(true,
                messagesService.getText("button.apl"),
                messagesService.getText("button.rpl"),
                messagesService.getText("button.return_main"));
    }

    public ReplyKeyboardMarkup getSportMenu()  {
        return getOneColumnKeyboard(true,
                messagesService.getText("button.soccer"),
                messagesService.getText("button.dota"),
                messagesService.getText("button.return_main"));
    }

    public ReplyKeyboardMarkup getFirstPageViewMenu() {
        return getOneRowKeyboard(true,
                messagesService.getText("button.menu"),
                messagesService.getText("button.next"));
    }
    public ReplyKeyboardMarkup getLastPageViewMenu() {
        return getOneRowKeyboard(true,
                messagesService.getText("button.prev"),
                messagesService.getText("button.menu"));
    }
    public ReplyKeyboardMarkup getViewMenu() {
        return getOneRowKeyboard(true,
                messagesService.getText("button.prev"),
                messagesService.getText("button.menu"),
                messagesService.getText("button.next"));
    }
}
