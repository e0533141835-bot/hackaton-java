package com.example.demo.controller;

import com.example.demo.model.Scenario;
import com.example.demo.model.SimulationResult;
import com.example.demo.model.User;
import com.example.demo.repository.ScenarioAssignmentRepository;
import com.example.demo.repository.SimulationResultRepository;
import com.example.demo.repository.ScenarioRepository; // הוספתי ייבוא
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

    @Autowired
    private SimulationResultRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScenarioAssignmentRepository assignmentRepository;

    @Autowired
    private ScenarioRepository scenarioRepository; // הוספתי כדי לשלוף תרחישים

    // פותר את שגיאת ה-404 ב-Dashboard
    @GetMapping("/scenarios")
    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    @PostMapping("/save")
    public SimulationResult saveResult(@RequestBody SimulationResult result, @RequestParam Long assignmentId) {

        SimulationResult saved = repository.save(result);

        assignmentRepository.findById(assignmentId).ifPresent(assignment -> {
            assignment.setStatus("COMPLETED");
            assignmentRepository.save(assignment);
        });

        if (saved.getFinalScore() != null && saved.getFinalScore() >= 90) {
            userRepository.findByAgentCode(saved.getTraineeId()).ifPresent(user -> {
                user.setRank(user.getRank() + 1);
                userRepository.save(user);
            });
        }
        return saved;
    }
}