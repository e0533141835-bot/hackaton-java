package com.example.demo.repository;

import com.example.demo.model.ScenarioAssignment;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScenarioAssignmentRepository extends JpaRepository<ScenarioAssignment, Long> {
    List<ScenarioAssignment> findByAgentAndStatus(User agent, String status);
    List<ScenarioAssignment> findByAgent(User agent);
}