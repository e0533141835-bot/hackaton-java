package com.example.demo.controller;

import com.example.demo.model.SimulationResult;
import com.example.demo.model.User;
import com.example.demo.repository.SimulationResultRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {

    @Autowired
    private SimulationResultRepository repository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public SimulationResult saveResult(@RequestBody SimulationResult result) {
        SimulationResult saved = repository.save(result);

        if (saved.getFinalScore() >= 90) {
            // חיפוש המשתמש לפי ה-agentCode ששמור ב-traineeId
            userRepository.findByAgentCode(saved.getTraineeId()).ifPresent(user -> {
                user.setRank(user.getRank() + 1);
                userRepository.save(user);
            });
        }
        return saved;
    }
}