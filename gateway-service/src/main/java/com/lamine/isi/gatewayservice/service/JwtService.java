package com.lamine.isi.gatewayservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    public String SECRET;


    public void validateToken(String token) throws Exception {
        try {
            // Parse et vérifie la signature + expiration
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            // Toute exception indique un token invalide ou expiré
            throw new Exception("Invalid or expired token");
        }
    }


    private Key getSignKey() {
        // Décodage Base64 de la clé
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        // Création de la clé HMAC
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
