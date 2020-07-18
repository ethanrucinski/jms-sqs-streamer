# JMS -> SQS Streamer

The JMS -> SQS Streamer library publishes messages from a JMS-compatible queue to an AWS SQS queue. SQS queues integrate with other AWS features and allow you to natively take advantage of queue auto-scaling, limitless message retention, at-least-once delivery, and IAM. Instead of being limited to reading messages in Java with JMS, using SQS as a secondary queue allows developers to read messages in languages other than Java and use cost-saving  technologies such as Lambda and EC2 auto-scaling.

## Getting Started

### 1. Project Setup

First, clone this repository to your machine. Make sure that you are using Java 1.8 and have maven installed. 
You will configure your connection to your JMS and SQS queues in a properties file before running your application for the first time. 

1. Create a file named `application.properties` in `src/main/resources`
2. Copy the code below into your new properties file and fill in the corresponding values

        spring.main.allow-bean-definition-overriding=true
        solace.jms.host=<jms host name and port>
        solace.jms.msgVpn=<vpn if required>
        solace.jms.clientUsername=<client username>
        solace.jms.clientPassword=<client password>

        jmslistener.queueName=<jms queue name>
        jmslistener.sqsQueueURL=<sqs queue URL>

You can run the application with this setup on your machine by typing `mvn spring-boot:run` into the command line. 

Keep in mind if you are running this outside of AWS, you will need to specify your AWS region and profile in your enviornment. See an example for setting these values on a unix-like computer:

        export AWS_REGION=us-east-1
        export AWS_PROFIlE=default

If you are running this on an EC2 or other AWS compute resource, make sure that the IAM role assigned to that resource has `sqs:SendMessage` permissions. 

To compile this project, run `mvn package` and you will find the .jar generated in the target folder. 