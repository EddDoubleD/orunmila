package com.edddoubled.orunmila.service;

import com.edddoubled.orunmila.exception.ResourceLoadingException;
import com.edddoubled.orunmila.model.ERole;
import com.edddoubled.orunmila.model.Employee;
import com.edddoubled.orunmila.model.Project;
import com.edddoubled.orunmila.model.Role;
import com.edddoubled.orunmila.util.ResourceLoader;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
@Slf4j
public class InitializationService {
    private static final String DIRECTORY = "orunmila-back/src/main/resources/data/";
    private static final String PROJECT = "projects.json";
    private static final String EMPLOYEE = "employees.json";
    private static final String MATRIX = "rolesMatrix.json";
    private static final String DEFAULT_IDP = "defaultIDP.json";

    EmployeeService employeeService;
    ProjectService projectService;
    RoleService roleService;


    public void init(boolean flag) {
        if (!flag) {
            log.info("The database will not be initialized");
            return;
        }
        // Delete all previously created employees, roles and projects
        roleService.removeAll();
        projectService.removeAll();
        employeeService.removeAll();

        // Create base roles
        roleService.saveRole(new Role(ERole.OWNER));
        roleService.saveRole(new Role(ERole.SUPEVISOR));
        Role defaultRole = roleService.saveRole(new Role(ERole.WORKER));
        roleService.saveRole(defaultRole);
        log.info("Base roles successfully created ");

        // Create projects by static resource
        try {
            Project[] projects = ResourceLoader.loadJsonFile(DIRECTORY, PROJECT, Project[].class);
            projectService.saveAll(Arrays.asList(projects));
        } catch (ResourceLoadingException e) {
            log.error(e.getMessage());
        }

        try {
            // Employee employees = ResourceLoader.loadJsonFile(DIRECTORY + "employees/", "popov_ivan.json", Employee.class);
            Employee[] employees = ResourceLoader.loadJsonFromDir(DIRECTORY + "employees/", Employee[].class);
            employeeService.saveAll(employees == null ? Collections.emptyList() : Arrays.asList(employees));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }
}
