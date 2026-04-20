package com.example.demo.repository;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // עבור שלב ה-Login
    Optional<User> findByIdNumber(String idNumber);

    // עבור שליפת נתונים וצ'אט בהמשך
    Optional<User> findByAgentCode(String agentCode);

    // חיפוש לפי אימייל (עבור ה-DataInitializer)
    Optional<User> findByEmail(String email);
}




