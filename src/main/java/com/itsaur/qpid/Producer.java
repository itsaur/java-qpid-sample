package com.itsaur.qpid;

import jakarta.jms.*;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsSession;
import org.apache.qpid.jms.message.JmsMapMessage;
import org.apache.qpid.jms.message.facade.JmsMapMessageFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Producer implements AutoCloseable {

    private final JmsConnection connection;
    private final JmsSession session;
    private final MessageProducer producer;
    private final Map<String, Queue> queues = new HashMap<>();

    public Producer(ConnectionProperties connectionProperties) throws JMSException {
        this.connection = Utils.createConnection(connectionProperties, "Producer-" + UUID.randomUUID());
        this.session = (JmsSession) connection.createSession();
        this.producer = session.createProducer(null);
        connection.start();
    }

    public void send(String queue, Map<String, Object> headers, Map<String, Object> body) throws JMSException {
        if (!queues.containsKey(queue)) {
            queues.put(queue, session.createQueue(queue));
        }

        JmsMapMessage message = (JmsMapMessage) session.createMapMessage();
        JmsMapMessageFacade facade = (JmsMapMessageFacade) message.getFacade();

        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                message.setObjectProperty(entry.getKey(), entry.getValue());
            }
        }

        if (body != null) {
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                facade.put(entry.getKey(), entry.getValue());
            }
        }

        producer.send(queues.get(queue), message, COMPLETION_LISTENER);
    }

    private static final CompletionListener COMPLETION_LISTENER = new CompletionListener() {
        @Override
        public void onCompletion(Message message) {
            System.out.println("Message sent");
        }

        @Override
        public void onException(Message message, Exception exception) {
            System.out.println("Sending message failed");
        }
    };

    @Override
    public void close() throws Exception {
        System.out.println("Closing consumer");
        producer.close();
        session.close();
        connection.close();
    }
}
