package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // חשוב מאוד כדי שהריאקט יוכל לגשת לשרת!
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // הרשמת מנהל
    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        user.setRole("ADMIN");
        return ResponseEntity.ok(userRepository.save(user));
    }

    // כניסת נציג - אימות הלינק הייחודי
    // כניסת נציג דרך תעודת זהות
    @GetMapping("/agent-login/{idNumber}")
    public ResponseEntity<?> agentLogin(@PathVariable String idNumber) {
        Optional<User> user = userRepository.findByIdNumber(idNumber);

        if (user.isPresent()) {
            // מחזירים את כל פרטי הנציג לאזור האישי שלו בריאקט
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(404).body("נציג לא נמצא במערכת. בדוק את תעודת הזהות.");
    }
}