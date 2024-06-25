package com.odk.repository;

import com.odk.entity.Role;
import com.odk.enums.TypeRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByRole(TypeRole role);
}
