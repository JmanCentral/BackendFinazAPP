package com.example.FinanzApp.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    //Crear un token

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //validad token de acceso
    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();

            return true;

        }catch (Exception e) {

            return false;

        }
    }

    //Obtener username del token

    public String getUsername(String token) {

        return getClaim(token, Claims::getSubject);
    }

    //Obtener un solo claim

    public <T> T getClaim(String token, Function<Claims,T> clazz) {

        Claims claims = extractAllClaims(token);
        return clazz.apply(claims);
    }



    // Obtener todos los claims del token

    public Claims extractAllClaims(String token){

        return  Jwts.parserBuilder().setSigningKey(getSignatureKey()).
                build().parseClaimsJws(token).getBody();

    }
    // Obtener firma del token
    public Key getSignatureKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
