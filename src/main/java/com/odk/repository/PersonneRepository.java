package com.odk.repository;

import com.odk.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Integer> {

    Personne findByEmail(String email);
}
