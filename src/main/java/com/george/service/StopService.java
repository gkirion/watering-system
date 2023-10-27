package com.george.service;

import com.george.model.Program;
import com.george.model.Stop;
import com.george.repository.StopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopService.class);

    @Autowired
    private ProgramService programService;

    @Autowired
    private StopRepository stopRepository;

    @Value("${watering.number_of_valves}")
    private Integer numberOfValves;


    public Stop create(Integer programId, Stop stop) {

        if (stop.getStopIndex() == null || stop.getDuration() == null
                || stop.getValves() == null || stop.getValves().isEmpty()) {
            throw new IllegalArgumentException("missing arguments");
        }

        for (Integer valve : stop.getValves()) {
            if (valve < 1 || valve > numberOfValves) {
                throw new IllegalArgumentException("valve indexes must be between " + 1 + " and " + numberOfValves);
            }
        }

        Program program = programService.findById(programId);
        stop.setProgram(program);
        return stopRepository.save(stop);
    }

    public Stop findById(Integer id) {
        Stop stop = stopRepository.findById(id).orElseThrow(() -> new RuntimeException("stop with id " + id + " does not exist"));
        return stop;
    }

    public List<Stop> getStopsOfProgram(Integer programId) {
        return stopRepository.findByProgramId(programId);
    }

    public Stop update(Integer id, Stop updatedStop) {

        if (updatedStop.getStopIndex() == null || updatedStop.getDuration() == null
                || updatedStop.getValves() == null || updatedStop.getValves().isEmpty()) {
            throw new IllegalArgumentException("missing arguments");
        }

        for (Integer valve : updatedStop.getValves()) {
            if (valve < 1 || valve > numberOfValves) {
                throw new IllegalArgumentException("valve indexes must be between " + 1 + " and " + numberOfValves);
            }
        }

        Stop stop = stopRepository.findById(id).orElseThrow(() -> new RuntimeException("stop with id " + id + " does not exist"));
        stop.setStopIndex(updatedStop.getStopIndex());
        stop.setDuration(updatedStop.getDuration());
        stop.setValves(updatedStop.getValves());
        return stopRepository.save(stop);
    }

    public void delete(Integer id) {
        Stop stop = stopRepository.findById(id).orElseThrow(() -> new RuntimeException("stop with id " + id + " does not exist"));
        stopRepository.delete(stop);
    }

}
