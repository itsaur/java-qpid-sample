This is a sample application on how to connect to a qpid broder and send and
receive messages.

To start a broker and test the code go to the `/docker` directory and run
```
docker compose up -d --build
```

This will start a qpid-broker. It will also build and start the `seed` image which will create 
a `TEST` queue in the broker. You can change the queue through `QUEUES` env variable in the 
compose.yaml file.
