package com.sensedia.antifraude.pubsub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class AntiFraudeProducer {

	private static final String CHANNEL = "antifraudeOutputChannel";

	@Value("${topic.out.name}")
	private String topicOutName;

	@Bean
	@ServiceActivator(inputChannel = CHANNEL)
	public MessageHandler messageSender(PubSubOperations pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, topicOutName);
	}

	@MessagingGateway(defaultRequestChannel = CHANNEL)
	public interface AntiFraudeGateway {
		void sendToPubsub(String input);
	}

}
