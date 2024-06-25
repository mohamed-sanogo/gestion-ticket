package com.odk.repository;

import com.odk.entity.Personne;
import com.odk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonneRepository extends JpaRepository<Personne, Integer> {

    Optional<Personne> findByEmail(String email);

    List<Personne> findByRole(Role role);

    Optional<Personne> findByIdAndRole(Integer id, Role orCreateRole);
}
