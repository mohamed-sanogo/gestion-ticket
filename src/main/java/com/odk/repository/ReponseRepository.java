package com.odk.repository;

import com.odk.entity.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Reponse, Integer> {
}
