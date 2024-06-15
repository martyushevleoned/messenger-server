package ru.urfu.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.*;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final Duration jstLifetime;

    public JwtUtils(@Value("${jwt.secret}") String secret, @Value("${jwt.lifetime}") Duration jstLifetime) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.jstLifetime = jstLifetime;
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = Map.of("roles", user.getAuthorities());
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jstLifetime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }
}