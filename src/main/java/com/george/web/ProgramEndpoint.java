package com.george.web;

import com.george.model.Program;
import com.george.model.Stop;
import com.george.service.ProgramService;
import com.george.service.StopService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/programs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProgramEndpoint {

    @Autowired
    private ProgramService programService;

    @Autowired
    private StopService stopService;

    @GetMapping
    @Timed(value = "programs.all", percentiles = {0.05, 0.95, 1.00})
    public List<Program> findAll() {
        return programService.findAll();
    }

    @GetMapping("/{id}")
    @Timed(value = "programs.id", percentiles = {0.05, 0.95, 1.00})
    public Program findById(@PathVariable Integer id) {
        return programService.findById(id);
    }

    @PutMapping("/{id}")
    @Timed(value = "programs.update", percentiles = {0.05, 0.95, 1.00})
    public Program update(@PathVariable Integer id, @RequestBody Program updatedProgram) {
        return programService.update(id, updatedProgram);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "programs.delete", percentiles = {0.05, 0.95, 1.00})
    public void delete(@PathVariable Integer id) {
        programService.delete(id);
    }

    @GetMapping("/{id}/stops")
    @Timed(value = "programs.stops", percentiles = {0.05, 0.95, 1.00})
    public List<Stop> getStopsOfProgram(@PathVariable Integer id) {
        return stopService.getStopsOfProgram(id);
    }

    @PostMapping("/{id}/stops")
    @Timed(value = "stops.create", percentiles = {0.05, 0.95, 1.00})
    public Stop create(@PathVariable Integer id, @RequestBody Stop stop) {
        return stopService.create(id, stop);
    }

    @PostMapping("/{id}/start")
    @Timed(value = "program.start", percentiles = {0.05, 0.95, 1.00})
    public void startProgram(@PathVariable Integer id) {
        programService.startProgram(id);
    }

    @PostMapping("/{id}/stop")
    @Timed(value = "program.stop", percentiles = {0.05, 0.95, 1.00})
    public void stopProgram(@PathVariable Integer id) {
        programService.stopProgram(id);
    }

}
