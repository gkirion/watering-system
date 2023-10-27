package com.george.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.george.domain.WateringStatusListener;
import com.george.model.Command;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Service
public class WateringQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(WateringQueue.class);

    @Value("${rabbitmq-host:localhost}")
    private String rabbitMQHost;

    private static final String WATERING_QUEUE_NAME = "watering-queue";

    private static final String COMMAND_QUEUE_NAME = "command-queue";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Channel channel;

    private Set<WateringStatusListener> wateringStatusListeners = new HashSet<>();

    @PostConstruct
    private void init() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        LOGGER.info("local address: {}, connecting to: {}", InetAddress.getLocalHost(), rabbitMQHost);
        connectionFactory.setHost(rabbitMQHost);
        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();

        LOGGER.info("declaring queue {}", WATERING_QUEUE_NAME);
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(WATERING_QUEUE_NAME, false, false, false, null);
        LOGGER.info("declared queue {}", declareOk);

        LOGGER.info("declaring queue {}", COMMAND_QUEUE_NAME);
        declareOk = channel.queueDeclare(COMMAND_QUEUE_NAME, false, false, false, null);
        LOGGER.info("declared queue {}", declareOk);


        channel.basicConsume(WATERING_QUEUE_NAME, true, (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("consumerTag: {}, message: {}", consumerTag, message);
            Integer currentStopIndex = OBJECT_MAPPER.readValue(message, Integer.class);
            LOGGER.info("current stop index: {}", currentStopIndex);

            for (WateringStatusListener wateringStatusListener : wateringStatusListeners) {
                wateringStatusListener.receiveWateringStatus(currentStopIndex);
            }

        }, consumerTag -> { LOGGER.info("consumer shutdown"); });
    }


    public void sendCommand(Command command) throws IOException {
        LOGGER.info("sending command: {}", command);
        channel.basicPublish("", COMMAND_QUEUE_NAME, null, OBJECT_MAPPER.writeValueAsBytes(command));
    }

    public void addWateringStatusListener(WateringStatusListener wateringStatusListener) {
        wateringStatusListeners.add(wateringStatusListener);
    }

}
