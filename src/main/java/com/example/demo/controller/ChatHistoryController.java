package com.example.demo.controller;

import com.example.demo.model.ChatMessage;
import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatHistoryController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history/{myId}/{otherId}")
    public List<ChatMessage> getHistory(@PathVariable String myId, @PathVariable String otherId) {
        return chatService.getHistory(myId, otherId);
    }
}