package com.alexeymarino.botclient.kafka.support;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

public class DeleteMessageDeserializer extends JsonDeserializer<DeleteMessage> {

}
