package com.alexeymarino.botserver.kafka;
import com.alexeymarino.botserver.service.InputMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
	private final InputMessageService inputMessageService;

	@KafkaListener(topics = "${topic.request}")
	public void executeTask(ConsumerRecord<String, Update> updateRecord) {
		log.info("The consume got Update: {}", updateRecord);
		inputMessageService.processInputUpdate(updateRecord.value());
	}
}
