package com.george.service;

import com.george.model.Program;
import com.george.repository.ProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramService.class);

    @Autowired
    private ProgramRepository programRepository;

    public Program create(Program program) {

        LOGGER.info("program: {}", program);
        if (program.getStartDate() == null || program.getPeriodDays() == null) {
            throw new IllegalArgumentException("start date and period days cannot be empty");
        }

        return programRepository.save(program);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public Program findById(Integer id) {
        Program program = programRepository.findById(id).orElseThrow(() -> new RuntimeException("program with id " + id + " does not exist"));
        return program;
    }

    public Program update(Integer id, Program updatedProgram) {

        if (updatedProgram.getStartDate() == null || updatedProgram.getPeriodDays() == null) {
            throw new IllegalArgumentException("start date and period days cannot be empty");
        }

        Program program = programRepository.findById(id).orElseThrow(() -> new RuntimeException("program with id " + id + " does not exist"));
        program.setStartDate(updatedProgram.getStartDate());
        program.setPeriodDays(updatedProgram.getPeriodDays());
        return programRepository.save(program);
    }

    public void delete(Integer id) {
        Program program = programRepository.findById(id).orElseThrow(() -> new RuntimeException("program with id " + id + " does not exist"));
        programRepository.delete(program);
    }

    public void startProgram(Integer id) {

    }

    public void stopProgram(Integer id) {

    }

}
