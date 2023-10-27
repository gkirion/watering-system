package com.george.repository;

import com.george.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {

    List<Stop> findByProgramId(Integer id);

}
