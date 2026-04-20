package com.example.demo.controller;

import com.example.demo.model.Scenario;
import com.example.demo.model.ScenarioAssignment;
import com.example.demo.model.User;
import com.example.demo.model.SimulationResult;
import com.example.demo.repository.ScenarioAssignmentRepository;
import com.example.demo.repository.ScenarioRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.SimulationResultRepository;
import com.example.demo.service.EmailService;
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

    @Autowired
    private ScenarioAssignmentRepository assignmentRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/add-agent")
    public User addAgent(@RequestBody User agent) {
        agent.setRole("AGENT");
        User savedAgent = userRepository.save(agent);

        try {
            emailService.sendWelcomeEmail(savedAgent.getEmail(), savedAgent.getFullName());
        } catch (Exception e) {
            System.out.println("שגיאה בשליחת המייל: " + e.getMessage());
        }

        return savedAgent;
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

    @PostMapping("/assign-to-agent")
    public ScenarioAssignment assignToAgent(@RequestParam Long agentId, @RequestParam Long scenarioId, @RequestParam String difficulty) {
        User agent = userRepository.findById(agentId).orElseThrow();
        Scenario scenario = scenarioRepository.findById(scenarioId).orElseThrow();

        ScenarioAssignment assignment = new ScenarioAssignment();
        assignment.setAgent(agent);
        assignment.setScenario(scenario);
        assignment.setDifficulty(difficulty);
        return assignmentRepository.save(assignment);
    }

    // שיוך תרחיש לכל הנציגים
    @PostMapping("/assign-to-all")
    public void assignToAll(@RequestParam Long scenarioId, @RequestParam String difficulty) {
        Scenario scenario = scenarioRepository.findById(scenarioId).orElseThrow();
        List<User> agents = userRepository.findAll().stream()
                .filter(u -> "AGENT".equals(u.getRole())).toList();

        for (User agent : agents) {
            ScenarioAssignment assignment = new ScenarioAssignment();
            assignment.setAgent(agent);
            assignment.setScenario(scenario);
            assignment.setDifficulty(difficulty);
            assignmentRepository.save(assignment);
        }
    }

    // חיפוש נציג לפי שם
    @GetMapping("/search-agent")
    public List<User> searchAgent(@RequestParam String name) {
        return userRepository.findByFullNameContainingIgnoreCaseAndRole(name, "AGENT");
    }
}
