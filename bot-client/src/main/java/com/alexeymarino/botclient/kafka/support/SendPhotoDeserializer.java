package com.alexeymarino.botclient.kafka.support;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class SendPhotoDeserializer extends JsonDeserializer<SendPhoto> {

}
