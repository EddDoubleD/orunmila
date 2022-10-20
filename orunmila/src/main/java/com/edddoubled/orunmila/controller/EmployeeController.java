package com.edddoubled.orunmila.controller;

import com.edddoubled.orunmila.model.Employee;
import com.edddoubled.orunmila.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping("/findByProject/{project}")
    public ResponseEntity<List<Employee>> findByProject(@PathVariable("project") String project) {
        List<Employee> employees = employeeService.findEmployeesByProject(project);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @GetMapping("/findByLogin/{login}")
    public ResponseEntity<Employee> findByLogin(@PathVariable("login") String login) {
        try {
            Optional<Employee> employees = employeeService.findEmployeeByLogin(login);
            if (employees.isPresent()) {
                return new ResponseEntity<>(employees.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.save(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.save(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }
}
