package com.odk.repository;

import com.odk.entity.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {

    Optional<Jwt> findByValeur(String valeur);

    Optional<Jwt> findByValeurAndDesactiverAndExpire(String valeur, boolean desactiver, boolean activer);
    @Query("FORM Jwt j WHERE j.expire = :expire AND j.desactiver = :desactiver AND j.utilisateur.email = :email")
    Optional<Jwt> findByUtilisateurValidToken(String email, boolean desactiver, boolean activer);
    @Query("FORM Jwt j WHERE j.utilisateur.email = :email")
    Stream<Jwt> findByPersonneEmail(String email);

    void deleteAllByExpireAndDesactiver(boolean expire, boolean desactiver);

}
