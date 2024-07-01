package com.odk.repository;

import com.odk.entity.Personne;
import com.odk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Integer> {

    Optional<Personne> findByEmail(String email);

    List<Personne> findByRole(Role role);

    Optional<Personne> findByIdAndRole(Integer id, Role orCreateRole);
}
