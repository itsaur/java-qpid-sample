package com.itsaur.qpid;

import jakarta.jms.JMSException;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionFactory;

public class Utils {

    static JmsConnection createConnection(ConnectionProperties connectionProperties, String id) throws JMSException {
        JmsConnectionFactory connectionFactory;
        if (connectionProperties.username() == null || connectionProperties.password() == null) {
            connectionFactory = new JmsConnectionFactory(connectionProperties.url());
        } else {
            connectionFactory = new JmsConnectionFactory(connectionProperties.url(), connectionProperties.username(), connectionProperties.password());
        }

        JmsConnection connection = (JmsConnection) connectionFactory.createConnection();
        connection.addConnectionListener(new ConnectionListener(id));
        return connection;
    }
}
