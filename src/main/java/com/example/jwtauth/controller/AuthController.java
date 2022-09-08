package com.example.jwtauth.controller;

import com.example.jwtauth.config.jwt.JWTProvider;
import com.example.jwtauth.dto.AuthRequest;
import com.example.jwtauth.dto.AuthResponse;
import com.example.jwtauth.dto.IntrospectRequest;
import com.example.jwtauth.entity.auth.User;
import com.example.jwtauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final UserService service;
    private final JWTProvider provider;

    @Autowired
    public AuthController(UserService service, JWTProvider provider) {
        this.service = service;
        this.provider = provider;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        service.register(user);
        return "OK";
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        Optional<User> userOptional = service.findByLoginAndPassword(request.getLogin(),
                request.getPassword());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return getAuth(request.getLogin());
    }

    @PostMapping("/introspect")
    public ResponseEntity<AuthResponse> introspect(@RequestBody IntrospectRequest request) {
        boolean isValid = provider.validate(request.getRefreshToken());
        if (isValid) {
            String login = provider.getLoginFromToken(request.getRefreshToken());
            return getAuth(login);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<AuthResponse> getAuth(String login) {
        String accessToken = provider.generateAccessToken(login);
        String refreshToken = provider.generateRefreshToken(login);

        return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
                HttpStatus.OK);
    }
}
