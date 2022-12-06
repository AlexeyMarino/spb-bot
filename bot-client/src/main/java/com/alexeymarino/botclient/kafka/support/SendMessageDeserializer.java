package com.alexeymarino.botclient.kafka.support;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendMessageDeserializer extends JsonDeserializer<SendMessage> {

}
