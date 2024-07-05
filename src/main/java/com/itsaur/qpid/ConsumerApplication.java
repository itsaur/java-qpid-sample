package com.itsaur.qpid;

import jakarta.jms.JMSException;

import java.util.concurrent.CountDownLatch;

public class ConsumerApplication {

    public static void main(String[] args) throws JMSException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Consumer consumer = new Consumer(new ConnectionProperties("amqp://localhost:5672", null, null), "TEST");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                consumer.close();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }));

        latch.await();
    }
}
