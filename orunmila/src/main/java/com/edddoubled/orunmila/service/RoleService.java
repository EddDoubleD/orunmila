package com.edddoubled.orunmila.service;

import com.edddoubled.orunmila.model.ERole;
import com.edddoubled.orunmila.model.Role;
import com.edddoubled.orunmila.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
@Slf4j
public class RoleService {

    RoleRepository repository;

    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public void removeAll() {
        repository.deleteAll();
        log.info("all roles have been removed");
    }

    public List<Role> saveAllRoles(Collection<Role> roles) {
        return repository.saveAll(roles);
    }

    public Role saveRole(Role role) {
        return repository.save(role);
    }

    public Optional<Role> findRoleByName(ERole role) {
        return repository.findByName(role);
    }
}
