package com.alexeymarino.botclient.kafka;


import com.alexeymarino.botclient.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerResponseListener {

	private TelegramBot telegramBot;

	@KafkaListener(topics = "${topic.response}")
	public void executeResponse(ConsumerRecord<String, SendMessage> response) {
		log.info("TaskListener. Request: key - {}, value - {}", response.key(), response.value());
		try {
			telegramBot.execute(response.value());
		} catch (TelegramApiException e) {
			log.info(e.getMessage());
		}
	}
}
