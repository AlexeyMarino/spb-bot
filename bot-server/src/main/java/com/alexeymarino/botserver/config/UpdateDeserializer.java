package com.alexeymarino.botserver.config;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateDeserializer extends JsonDeserializer<Update> {

}
