package com.odk.config;

import com.odk.entity.Jwt;
import com.odk.entity.Personne;
import com.odk.repository.JwtRepository;
import com.odk.service.PersonneService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

@Transactional
@AllArgsConstructor
@Service
@Slf4j
public class JwtService {
    private final String ENCRIPTION_KEY = "496cef9d7adbcc5fe029b0fe9459bb445d70a49fdbe897080dbf4c0c0bdce140";
    private PersonneService personneService;
    private JwtRepository jwtRepository;

    public Jwt tokenByValue(String valeur) {
        return this.jwtRepository.findByValeurAndDesactiverAndExpire(
                valeur,
                false,
                false
                )
                .orElseThrow(()-> new RuntimeException("Token invalide ou inconnu"));
    }

    public Map<String, String> generate(String username){
        Personne personne = (Personne) this.personneService.loadUserByUsername(username);
        final Map<String, String> jwtMap = JwtService.this.generateJwt(personne);
        final Jwt jwt = Jwt
                .builder()
                .valeur(jwtMap.get("bearer"))
                .desactiver(false)
                .expire(false)
                .personne(personne)
                .build();
        this.jwtRepository.save(jwt);
        return jwtMap;

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

    private void disableTokens(Personne personne){
        final List<Jwt> jwtList = this.jwtRepository.findByPersonneEmail(personne.getEmail()).peek(
                jwt -> {
                    jwt.setDesactiver(true);
                    jwt.setExpire(true);
                }
        ).collect(Collector.toList());
        this.jwtRepository.saveAll(jwtList);
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

    public void deconnexion() {
        Personne personne = (Personne) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = this.jwtRepository.findByUtilisateurValidToken(
                personne.getEmail(), false, false
        ).orElseThrow(()-> new RuntimeException("Token Invalide"));
        jwt.setExpire(true);
        jwt.setDesactiver(true);
        this.jwtRepository.save(jwt);
    }

    //@Scheduled(cron = "0 1/* * * * *")
    @Scheduled(cron = "@daily")
    public void removeUseLessJwt(){
        log.info("Suppression des tokens à {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactiver(true, true);
    }
}
