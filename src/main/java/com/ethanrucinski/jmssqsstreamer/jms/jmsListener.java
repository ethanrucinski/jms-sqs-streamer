package com.ethanrucinski.jmssqsstreamer.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@EnableJms
@Component
public class jmsListener {

    // Class responsible for listening to first queue and receiving each message
    @JmsListener(destination = "${jmslistener.queueName}", containerFactory = "jmsListenerContainerFactory")
    public void handle(final Message message) {
        if (message instanceof TextMessage) {
            final TextMessage tm = (TextMessage) message;
            try {
                System.out.println(tm.getText());
            } catch (final JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(message.toString());
        }
    }
}