package com.sensedia.antifraude.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

@Configuration
public class DataStoreConfig {
	
	@Bean
	public Datastore cloudDatastoreService() {
		return DatastoreOptions.getDefaultInstance().getService();
	}

}
