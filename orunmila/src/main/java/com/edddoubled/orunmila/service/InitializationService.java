package com.edddoubled.orunmila.service;

import com.edddoubled.orunmila.dto.Matrix;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
@Slf4j
public class InitializationService {

    private static final String DIRECTORY = "orunmila/src/main/resources/data/";
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
        log.info("Base roles successfully created ");

        // Create projects by static resource
        try {
            Project[] projects = ResourceLoader.loadJsonFile(DIRECTORY, PROJECT, Project[].class);
            projectService.saveAll(Arrays.asList(projects));
        } catch (ResourceLoadingException e) {
            log.error(e.getMessage());
        }

        try {
            Employee.IDPNode[] defaultTemplate = ResourceLoader.loadJsonFile(DIRECTORY, DEFAULT_IDP, Employee.IDPNode[].class);
            Matrix[] rolesMatrix = ResourceLoader.loadJsonFile(DIRECTORY, MATRIX, Matrix[].class);
            Map<String, ERole> matrix = Arrays.stream(rolesMatrix)
                    .collect(
                            Collectors.toMap(Matrix::getLogin, m -> ERole.valueOf(m.getRole()),
                                    (a, b) -> b));

            Employee[] employees = ResourceLoader.loadJsonFile(DIRECTORY, EMPLOYEE, Employee[].class);
            for (Employee employee : employees) {
                Optional<Role> role = roleService.findRoleByName(matrix.get(employee.getLogin()));
                if (role.isPresent()) {
                    employee.setRole(role.get());
                } else {
                    employee.setRole(defaultRole);
                }
                employee.setIdpTemplate(Arrays.asList(defaultTemplate));
                employeeService.save(employee);
            }
        } catch (ResourceLoadingException e) {
            log.error(e.getMessage());
        }

    }
}
