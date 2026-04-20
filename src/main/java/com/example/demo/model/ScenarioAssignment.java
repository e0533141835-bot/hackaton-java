package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "scenario_assignments")
@Data
public class ScenarioAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User agent;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    private String difficulty;
    private String status;

    private LocalDateTime assignedAt;

    @PrePersist
    protected void onCreate() {
        this.assignedAt = LocalDateTime.now();
        if (this.status == null) this.status = "PENDING";
    }
}