package ru.urfu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.model.dto.AuthDto;
import ru.urfu.model.dto.RegistrationDto;
import ru.urfu.service.UserService;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody RegistrationDto registrationDto) {

        try {
            userService.signUpUser(registrationDto);
            return ResponseEntity.ok("Пользователь зарегистрирован");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody AuthDto authDto) {

        try {
            return ResponseEntity.ok(userService.signInUser(authDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
