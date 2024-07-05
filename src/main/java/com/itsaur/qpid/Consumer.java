package com.itsaur.qpid;

import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsSession;
import org.apache.qpid.jms.message.JmsMapMessage;
import org.apache.qpid.jms.message.facade.JmsMapMessageFacade;

import java.util.Enumeration;
import java.util.UUID;

public class Consumer implements AutoCloseable {

    private final JmsConnection connection;
    private final JmsSession session;
    private final MessageConsumer consumer;

    public Consumer(ConnectionProperties connectionProperties, String queue) throws JMSException {
        this.connection = Utils.createConnection(connectionProperties, "Consumer-" + UUID.randomUUID());
        this.session = (JmsSession) connection.createSession();
        this.consumer = session.createConsumer(session.createQueue(queue));
        connection.start();
        listen();
    }

    private void listen() throws JMSException {
        consumer.setMessageListener(message -> {
            try {
                System.out.println("--- Received message ---");
                JmsMapMessage mapMessage = (JmsMapMessage) message;
                JmsMapMessageFacade facade = (JmsMapMessageFacade) mapMessage.getFacade();

                System.out.println("\tHeaders: ");
                Enumeration<?> headerEnumeration = mapMessage.getPropertyNames();
                while (headerEnumeration.hasMoreElements()) {
                    String header = (String) headerEnumeration.nextElement();
                    System.out.println("\t\t" + header + ": " + mapMessage.getObjectProperty(header));
                }

                System.out.println("\tBody: ");
                Enumeration<String> bodyEnumeration = facade.getMapNames();
                while (bodyEnumeration.hasMoreElements()) {
                    String property = bodyEnumeration.nextElement();
                    System.out.println("\t\t" + property + ": " + facade.get(property));
                }
            } catch (JMSException e) {
                System.out.println("error: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public void close() throws JMSException {
        System.out.println("Closing consumer");
        connection.close();
        session.close();
        connection.close();
    }
}
