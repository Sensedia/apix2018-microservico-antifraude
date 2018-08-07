package com.sensedia.antifraude.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PubSubConfig {
	
	@Bean
	public MessageChannel pubsubInputChannel() {
		return new DirectChannel();
	}
	
}
