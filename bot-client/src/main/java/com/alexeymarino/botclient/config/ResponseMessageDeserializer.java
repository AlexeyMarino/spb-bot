package com.alexeymarino.botclient.config;

import com.alexeymarino.botcore.model.ResponseMessage;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class ResponseMessageDeserializer extends JsonDeserializer<ResponseMessage> {
}
