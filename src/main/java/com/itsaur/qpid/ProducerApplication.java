package com.itsaur.qpid;

import java.util.List;
import java.util.Map;

public class ProducerApplication {

    public static void main(String[] args) throws Exception {
        try(Producer producer = new Producer(new ConnectionProperties("amqp://localhost:5672", null, null))) {
            String QUEUE = "TEST";

            producer.send(QUEUE,
                    Map.of("header", "header-value"),
                    Map.of("propertyA", "a", "propertyB", 1));

            producer.send(QUEUE,
                    Map.of("header", "another-header-value"),
                    Map.of("list", List.of("a", "b", "c")));
        }
    }
}
