package ru.urfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.model.dto.AuthDto;
import ru.urfu.model.dto.RegistrationDto;
import ru.urfu.model.dto.UserInfoDto;
import ru.urfu.model.entity.User;
import ru.urfu.model.repository.UserRepository;
import ru.urfu.config.JwtUtils;

import java.security.Principal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public void signUpUser(RegistrationDto registrationDto) throws RuntimeException {

        if (userRepository.findByUsername(registrationDto.username()).isPresent())
            throw new RuntimeException("Указанный Username уже занят");

        if (userRepository.findByEmail(registrationDto.email()).isPresent())
            throw new RuntimeException("Указанный Email уже занят");

        User user = new User();
        user.setUsername(registrationDto.username());
        user.setEmail(registrationDto.email());
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        userRepository.save(user);
    }

    public String signInUser(AuthDto authDto) throws RuntimeException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.username(), authDto.password()));
            User user = userRepository.findByUsername(authDto.username()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            return jwtUtils.generateToken(user);
        } catch (Exception e) {
            throw new RuntimeException("Пользователь не найден");
        }
    }

    public UserInfoDto getUserInfo(Principal principal) throws RuntimeException {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return new UserInfoDto(user.getUsername(), user.getEmail());
    }
}
