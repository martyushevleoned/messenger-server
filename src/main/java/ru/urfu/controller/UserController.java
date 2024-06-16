package ru.urfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.service.ChatService;
import ru.urfu.service.UserService;

import java.security.Principal;

@RestController
public class UserController {

    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public UserController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @GetMapping("/userInfo")
    private ResponseEntity<?> getUserInfo(Principal principal) {
        try {
            return ResponseEntity.ok(userService.getUserInfo(principal));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/createChat")
    private ResponseEntity<?> createChat(Principal principal, @RequestBody String chatName) {
        try {
            chatService.crateChat(principal, chatName);
            return ResponseEntity.ok("Чат создан");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getChats")
    private ResponseEntity<?> getChats(Principal principal) {
        try {
            return ResponseEntity.ok(chatService.getChats(principal));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
