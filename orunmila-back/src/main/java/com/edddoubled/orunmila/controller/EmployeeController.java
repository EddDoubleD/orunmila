package com.edddoubled.orunmila.controller;

import com.edddoubled.orunmila.dto.request.UpdateIDPRequest;
import com.edddoubled.orunmila.dto.response.PageableEmployees;
import com.edddoubled.orunmila.exception.EmployeeLoadingException;
import com.edddoubled.orunmila.model.Employee;
import com.edddoubled.orunmila.service.EmployeeService;
import com.edddoubled.orunmila.util.JsonUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<PageableEmployees> getPageableEmployees(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        PageableEmployees response = new PageableEmployees();
        Page<Employee> pageable = employeeService.findPageableEmployees(page - 1, size);
        if (CollectionUtils.isEmpty(pageable.getContent()))  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        response.setPage(page);
        response.setTotalPages(pageable.getTotalPages());
        response.setItemsSize(pageable.getTotalElements());
        response.setEmployees(pageable.getContent());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pageable/login")
    public ResponseEntity<PageableEmployees> getPageableEmployeesByLogin(@RequestParam("login") String login,
                                                                  @RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        PageableEmployees response = new PageableEmployees();
        Page<Employee> pageable;
        try {
            pageable = employeeService.findEmployeeByLogin(login, page - 1, size);
        } catch (EmployeeLoadingException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (CollectionUtils.isEmpty(pageable.getContent()))  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        response.setPage(page);
        response.setTotalPages(pageable.getTotalPages());
        response.setItemsSize(pageable.getTotalElements());
        response.setEmployees(pageable.getContent());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


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

    @PutMapping("/updateSchema")
    public ResponseEntity<Employee> updateIDPSchema(@RequestBody UpdateIDPRequest request) {
        Optional<Employee> updateEmployee = employeeService.findEmployeeByLogin(request.getLogin());
        if (updateEmployee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Employee employee = updateEmployee.get();
        // "class" is a reserved word in java, but it is used in gojs
        // replace class -> clazz
        String data = request.getData().replace("class", "clazz");
        employee.setData(JsonUtils.deserialize(data, UpdateIDPRequest.Model.class).getNodeDataArray());
        employee = employeeService.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
