package com.ethanrucinski.jmssqsstreamer;

import java.util.Timer;
import java.util.TimerTask;

import com.ethanrucinski.jmssqsstreamer.jms.jmsListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Component;

@SpringBootApplication
@RestController
@ComponentScan("com.ethanrucinski.jmssqsstreamer.*")
@Component
public class JmsSqsStreamerApplication {

	static Timer timer;
	static int lastCount = 0;
	public static long lastlogtime = System.currentTimeMillis();

	static class MetricsTask extends TimerTask {
		public void run() {
			int newMessages = jmsListener.count - lastCount;
			long interval = System.currentTimeMillis() - lastlogtime;
			float rate = 1000*((float)newMessages)/((float)interval);
			System.out.printf("Messages: %d ", jmsListener.count - lastCount);
			System.out.printf("Rate: %.2f/s \n", rate);
			lastlogtime = System.currentTimeMillis();
			lastCount = jmsListener.count;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(JmsSqsStreamerApplication.class, args);
		int seconds = 2;
		lastlogtime= System.currentTimeMillis();
		timer = new Timer();
		timer.schedule(new MetricsTask(), 2000, seconds * 1000);
	}

	// Basic health check for the application will return "OK" when running
	@RequestMapping("/healthz")
	public String health() {
		return "OK";
	}

}
