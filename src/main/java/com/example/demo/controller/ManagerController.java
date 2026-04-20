package com.example.demo.controller;

import com.example.demo.model.Scenario;
import com.example.demo.model.User;
import com.example.demo.model.SimulationResult;
import com.example.demo.repository.ScenarioRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.SimulationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "*")
public class ManagerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private SimulationResultRepository resultRepository;

    // 1. הוספת נציג (מייצר לינק אוטומטית)
    @PostMapping("/add-agent")
    public User addAgent(@RequestBody User agent) {
        agent.setRole("AGENT");
        return userRepository.save(agent);
    }

    // 2. הוספת תרחיש (למשל: לקוח עצבני)
    @PostMapping("/add-scenario")
    public Scenario addScenario(@RequestBody Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    // 3. הצגת כל התרחישים (כדי שהנציג יבחר אחד)
    @GetMapping("/scenarios")
    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    // 4. דוח למנהלת: כל תוצאות הסימולציות
    @GetMapping("/all-results")
    public List<SimulationResult> getAllResults() {
        return resultRepository.findAll();
    }
}
