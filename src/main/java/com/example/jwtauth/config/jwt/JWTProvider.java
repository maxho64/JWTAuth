package com.example.jwtauth.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JWTProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateAccessToken(String login) {
        return generateToken(login, TimeUnit.MINUTES.toMillis(5));
    }

    public String generateRefreshToken(String login) {
        return generateToken(login, TimeUnit.DAYS.toMillis(1));
    }

    private String generateToken(String login, Long expirationTime) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validate(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token){
        return parseToken(token).getBody().getSubject();
    }

    private Jws<Claims> parseToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }
}
