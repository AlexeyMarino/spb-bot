package com.alexeymarino.botserver.service.support;

import com.alexeymarino.botserver.service.support.LocaleMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


/**
 * Формирует готовые ответные сообщения в чат.
 */
@Service
@RequiredArgsConstructor
public class TextMessagesService {

    private final LocaleMessageService localeMessageService;

    public SendMessage getReplyMessage(long chatId, String replyMessage) {
        return new SendMessage(String.valueOf(chatId), localeMessageService.getMessage(replyMessage));
    }

    public String getText(String code) {
        return localeMessageService.getMessage(code);
    }

}
