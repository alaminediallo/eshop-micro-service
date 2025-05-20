package com.lamine.isi.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    public String SECRET;


    public boolean validateToken(final String token) {
        try {
            // Parse et vérifie la signature + expiration
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Toute exception indique un token invalide ou expiré
            return false;
        }
    }

    public String generateToken(String userName) {
        // Pas de claims supplémentaires ici, mais on peut en ajouter
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        long nowMillis = System.currentTimeMillis();
        Date issuedAt = new Date(nowMillis);
        // Le token expire dans 60 minutes (1000 ms * 60 sec * 60 min).
        Date expiration = new Date(nowMillis + 1000 * 60 * 60);

        return Jwts.builder()
                .setClaims(claims)                         // Claims personnalisés
                .setSubject(userName)                      // Le sujet (sub)
                .setIssuedAt(issuedAt)                     // Date d'émission (iat)
                .setExpiration(expiration)                 // Date d'expiration (exp)
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Signature HMAC-SHA256
                .compact();                                // Génère la String JWT
    }

    private Key getSignKey() {
        // Décodage Base64 de la clé
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        // Création de la clé HMAC
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
