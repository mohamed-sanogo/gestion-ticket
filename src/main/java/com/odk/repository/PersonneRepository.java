package com.odk.repository;

import com.odk.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonneRepository extends JpaRepository<Personne, Integer> {

    Optional<Personne> findByEmail(String email);
}
