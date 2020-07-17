package com.ethanrucinski.jmssqsstreamer.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

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

    private static String sqsUrl = "${sqs.sqsQueueURL}";

    @JmsListener(destination = "${jmslistener.queueName}", containerFactory = "jmsListenerContainerFactory")
    public void handle(final Message message) {
        if (message instanceof TextMessage) {
            final TextMessage tm = (TextMessage) message;
            try {
                // System.out.println(tm.getText());

                try {
                    SendMessageRequest send_msg_request = new SendMessageRequest().withQueueUrl(sqsUrl)
                            .withMessageBody(tm.getText()).withDelaySeconds(0);
                    sqs.sendMessage(send_msg_request);
                    System.out.println(sqsUrl);

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