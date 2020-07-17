package com.ethanrucinski.jmssqsstreamer.jms;

import java.sql.Timestamp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@EnableJms
@Component
public class jmsListener {

    // Class responsible for listening to first queue and receiving each message
    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    @Value("${jmslistener.sqsQueueURL}")
    String sqsUrl;

    private long lastmessage = System.currentTimeMillis();

    @JmsListener(destination = "${jmslistener.queueName}", containerFactory = "jmsListenerContainerFactory")
    public void handle(final Message message) {
        if (message instanceof TextMessage) {
            final TextMessage tm = (TextMessage) message;
            try {
                try {
                    SendMessageRequest send_msg_request = new SendMessageRequest().withQueueUrl(sqsUrl)
                            .withMessageBody(tm.getText()).withDelaySeconds(0);
                    sqs.sendMessage(send_msg_request);
                    System.out.printf("Message time: %dms\n", System.currentTimeMillis() - lastmessage);
                    lastmessage = System.currentTimeMillis();
                } catch (AmazonSQSException e) {
                    e.printStackTrace();
                }
            } catch (final JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(message.toString());
        }
    }

}