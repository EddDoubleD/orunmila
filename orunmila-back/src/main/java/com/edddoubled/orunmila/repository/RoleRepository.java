package com.edddoubled.orunmila.repository;

import com.edddoubled.orunmila.model.ERole;
import com.edddoubled.orunmila.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(ERole role);
}
