package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String agentCode; // המזהה הפנימי לעבודה (למשל: "AG100")

    @Column(unique = true)
    private String idNumber; // שדה חדש לת"ז

    private String password;
    private String fullName;
    private String role; // "ADMIN" או "AGENT"

    @Column(name = "user_rank")
    private int rank = 1;

    @Column(unique = true)
    private String uniqueLink;

    @PrePersist
    public void generateLink() {
        if ("AGENT".equals(this.role)) {
            this.uniqueLink = UUID.randomUUID().toString();
        }
    }
}

