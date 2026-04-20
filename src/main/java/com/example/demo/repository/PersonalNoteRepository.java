package com.example.demo.repository;

import com.example.demo.model.PersonalNote;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonalNoteRepository extends JpaRepository<PersonalNote, Long> {
    List<PersonalNote> findByAgent(User agent);
}