package com.odk.config;

import com.odk.entity.Personne;
import com.odk.service.PersonneService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {
    private final String ENCRIPTION_KEY = "496cef9d7adbcc5fe029b0fe9459bb445d70a49fdbe897080dbf4c0c0bdce140";
    private PersonneService personneService;

    public Map<String, String> generate(String username){
        Personne personne = (Personne) this.personneService.loadUserByUsername(username);
        return this.generateJwt(personne);

    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }


    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    final long currentTime = System.currentTimeMillis();
    final long expirationTime = currentTime + 30 * 60 * 100;


    private Map<String, String> generateJwt(Personne personne) {
        Map<String, Object> claims = Map.of(
                "nom", personne.getNom(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, personne.getEmail()
        );
        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(personne.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("bearer", bearer);
    }
    private Key getKey(){
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

}
