package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
public class AgentController {

    @Autowired
    private ScenarioAssignmentRepository assignmentRepository;
    @Autowired
    private PersonalNoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimulationResultRepository resultRepository;

    // 1. שליפת סימולציות ששויכו לנציג ועדיין לא בוצעו
    @GetMapping("/{agentId}/pending-simulations")
    public List<ScenarioAssignment> getPendingSimulations(@PathVariable Long agentId) {
        User agent = userRepository.findById(agentId).orElseThrow();
        return assignmentRepository.findByAgentAndStatus(agent, "PENDING");
    }

    // 2. שליפת היסטוריית סימולציות אישית
    @GetMapping("/{agentCode}/history")
    public List<SimulationResult> getMyHistory(@PathVariable String agentCode) {
        return resultRepository.findByTraineeId(agentCode);
    }

    // 3. ניהול פתקים נדבקים
    @GetMapping("/{agentId}/notes")
    public List<PersonalNote> getMyNotes(@PathVariable Long agentId) {
        User agent = userRepository.findById(agentId).orElseThrow();
        return noteRepository.findByAgent(agent);
    }

    @PostMapping("/{agentId}/add-note")
    public PersonalNote addNote(@PathVariable Long agentId, @RequestBody String content) {
        User agent = userRepository.findById(agentId).orElseThrow();
        PersonalNote note = new PersonalNote();
        note.setAgent(agent);
        note.setContent(content);
        return noteRepository.save(note);
    }

    @DeleteMapping("/delete-note/{noteId}")
    public void deleteNote(@PathVariable Long noteId) {
        noteRepository.deleteById(noteId);
    }
}