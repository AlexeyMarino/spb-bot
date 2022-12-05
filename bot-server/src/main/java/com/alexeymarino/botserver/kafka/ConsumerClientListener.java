package com.alexeymarino.botserver.kafka;
import com.alexeymarino.botserver.botapi.ServerFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerClientListener {

	private final ServerFacade serverFacade;

	@KafkaListener(topics = "${topic.request}")
	public void executeTask(ConsumerRecord<String, Update> updateRecord) {
		serverFacade.handleUpdate(updateRecord.value());
	}
}
