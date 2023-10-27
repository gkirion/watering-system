package com.george.service;

import com.george.domain.WateringStatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WateringService implements WateringStatusListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WateringService.class);

    private WateringQueue wateringQueue;

    public WateringService(WateringQueue wateringQueue) {
        wateringQueue.addWateringStatusListener(this);
    }

    @Override
    public void receiveWateringStatus(Integer currentStopIndex) {
        LOGGER.info("watering status: {}", currentStopIndex);
    }

}
