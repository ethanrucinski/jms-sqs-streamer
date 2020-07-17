package com.ethanrucinski.jmssqsstreamer.config;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class config {

	
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    
		DefaultJmsListenerContainerFactory listenerFactory = new DefaultJmsListenerContainerFactory();
	    configurer.configure(listenerFactory, connectionFactory);
	    listenerFactory.setTransactionManager(null);
	    listenerFactory.setSessionTransacted(false);
	    listenerFactory.setConcurrency("2-15");
	    return listenerFactory;
	}
	
}
