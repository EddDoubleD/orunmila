package com.edddoubled.orunmila.service;


import com.edddoubled.orunmila.model.Employee;
import com.edddoubled.orunmila.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
@Slf4j
public class EmployeeService {

    EmployeeRepository employeeRepository;

    MongoTemplate template;


    public Optional<Employee> findEmployeeByName(String name) {
        return employeeRepository.findEmployeeByName(name);
    }

    public Optional<Employee> findEmployeeByLogin(String login) {
        return employeeRepository.findEmployeeByLogin(login);
    }

    /**
     * Generates a query in the database to select a collection by size and position
     *
     * @param page collection position
     * @param size collection dimension
     * @return collection of the given size by position
     */
    public Page<Employee> findPageableEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }


    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> saveAll(Collection<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    public void removeAll() {
        employeeRepository.deleteAll();
        log.info("all employees have been removed");
    }

    public List<Employee> findEmployeesByProject(String project) {
        return employeeRepository.findEmployeesByProject(project);
    }
 }
