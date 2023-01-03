package com.alexeymarino.botserver.service.support;

import java.io.File;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
public class BotMethodService {
    public SendMessage getSendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = getSendMessage(chatId, text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        sendMessage.disableWebPagePreview();
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    public SendMessage getSendMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = getSendMessage(chatId, text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage getSendMessage(Long chatId, String text) {
        return new SendMessage(String.valueOf(chatId), text);
    }

    public SendPhoto getSendPhoto(Long chatId, File file, ReplyKeyboardMarkup keyboardMarkup, String caption) {
        SendPhoto sendPhoto = new SendPhoto(String.valueOf(chatId), new InputFile(file));
        sendPhoto.setReplyMarkup(keyboardMarkup);
        sendPhoto.setCaption(caption);
        return sendPhoto;
    }

    public EditMessageText getEditMessageText(Long chatId, String newText, InlineKeyboardMarkup keyboard) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setText(newText);
        editMessageText.setReplyMarkup(keyboard);
        return editMessageText;
    }
}
