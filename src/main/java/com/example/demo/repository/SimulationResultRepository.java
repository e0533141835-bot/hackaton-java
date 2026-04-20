package com.example.demo.repository;

import com.example.demo.model.SimulationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SimulationResultRepository extends JpaRepository<SimulationResult, Long> {
    // השם חייב להיות תואם לשדה traineeId
    List<SimulationResult> findByTraineeId(String traineeId);
}

