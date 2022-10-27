package com.edddoubled.orunmila.controller;


import com.edddoubled.orunmila.model.ERole;
import com.edddoubled.orunmila.model.Role;
import com.edddoubled.orunmila.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        try {
            List<Role> employees = roleService.getAllRoles();
            if (!CollectionUtils.isEmpty(employees)) {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name) {
        try {
            Optional<Role> employee = roleService.findRoleByName(ERole.valueOf(name.toUpperCase(Locale.ROOT)));
            if (employee.isPresent()) {
                return new ResponseEntity<>(employee.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
