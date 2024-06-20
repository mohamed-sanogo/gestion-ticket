package com.odk.repository;

import com.odk.entity.BaseDeConnaissance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository extends JpaRepository<BaseDeConnaissance, Integer> {
}
