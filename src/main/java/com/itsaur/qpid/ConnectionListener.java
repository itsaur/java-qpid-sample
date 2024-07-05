package com.itsaur.qpid;

import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;

import java.net.URI;

final class ConnectionListener implements JmsConnectionListener {

    private final String id;

    ConnectionListener(String id) {
        this.id = id;
    }

    @Override
    public void onConnectionEstablished(URI uri) {
        System.out.println(id + " --> Connection established: " + uri);
    }

    @Override
    public void onConnectionFailure(Throwable throwable) {
        System.out.println(id + " --> Connection failed: " + throwable);
    }

    @Override
    public void onConnectionInterrupted(URI uri) {
        System.out.println(id + " --> Connection interrupted: " + uri);
    }

    @Override
    public void onConnectionRestored(URI uri) {
        System.out.println(id + " --> Connection restored: " + uri);
    }

    @Override
    public void onInboundMessage(JmsInboundMessageDispatch jmsInboundMessageDispatch) {
        System.out.println(id + " --> Inbound message");
    }

    @Override
    public void onSessionClosed(Session session, Throwable throwable) {
        System.out.println(id + " --> Session closed: " + throwable);
    }

    @Override
    public void onConsumerClosed(MessageConsumer messageConsumer, Throwable throwable) {
        System.out.println(id + " --> Consumer closed: " + throwable);
    }

    @Override
    public void onProducerClosed(MessageProducer messageProducer, Throwable throwable) {
        System.out.println(id + " --> Producer closed: " + throwable);
    }
}
