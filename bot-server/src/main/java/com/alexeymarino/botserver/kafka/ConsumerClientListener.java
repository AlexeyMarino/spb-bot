package com.alexeymarino.botserver.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerClientListener {


	@KafkaListener(topics = "${topic.request}")
	public void executeTask(ConsumerRecord<String, SendMessage> task) {

	}
}
