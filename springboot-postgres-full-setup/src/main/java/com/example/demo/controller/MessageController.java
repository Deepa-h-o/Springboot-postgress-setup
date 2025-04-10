package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    private MessageRepository repository;

    @PostMapping("/message")
    public Message saveMessage(@RequestBody Message message) {
        return repository.save(message);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}