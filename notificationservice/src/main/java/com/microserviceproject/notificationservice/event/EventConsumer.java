package com.microserviceproject.notificationservice.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {

	@RetryableTopic(
			attempts = "4", // 3 topic retry + 1 topic DLQ
			backoff = @Backoff(delay = 1000, multiplier = 2),
			autoCreateTopics = "true",
			dltStrategy = DltStrategy.FAIL_ON_ERROR,
			include = {RetriableException.class, RuntimeException.class}
	)
	@KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
	public void listen(String message) {
		log.info("Received message: " + message);
		// processing message
//		throw new RuntimeException("Error test");
	}

	@DltHandler
	void processDltMessage(@Payload String messsage) {
		log.info("DLT receive message: " + messsage);
	}
}