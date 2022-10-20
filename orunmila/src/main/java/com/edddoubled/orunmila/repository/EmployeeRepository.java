package com.edddoubled.orunmila.repository;

import com.edddoubled.orunmila.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findEmployeeByName(String name);

    Optional<Employee> findEmployeeByLogin(String login);

    Page<Employee> findAll(Pageable pageable);

    @Query("{ 'projects' : ?0 }")
    List<Employee> findEmployeesByProject(String project);
}
