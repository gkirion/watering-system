package com.george.web;

import com.george.model.Stop;
import com.george.service.StopService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/stops", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopEndpoint {

    @Autowired
    private StopService stopService;


    @GetMapping("/{id}")
    @Timed(value = "stops.id", percentiles = {0.05, 0.95, 1.00})
    public Stop findById(@PathVariable Integer id) {
        return stopService.findById(id);
    }

    @PutMapping("/{id}")
    @Timed(value = "stops.update", percentiles = {0.05, 0.95, 1.00})
    public Stop update(@PathVariable Integer id, @RequestBody Stop stop) {
        return stopService.update(id, stop);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "stops.delete", percentiles = {0.05, 0.95, 1.00})
    public void delete(@PathVariable Integer id) {
        stopService.delete(id);
    }

}
