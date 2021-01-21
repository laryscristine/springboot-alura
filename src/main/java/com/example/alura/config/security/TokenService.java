package com.example.alura.config.security;

import com.example.alura.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {

        Usuario logged = (Usuario) authentication.getPrincipal();
        Date today = new Date();
        Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration)); /// Necessário os valores serem Long
        return Jwts.builder()
                .setIssuer("API do Fórum da Alura")
                .setSubject(logged.getId().toString())
                .setIssuedAt(today)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Long getIdUsuario(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}
