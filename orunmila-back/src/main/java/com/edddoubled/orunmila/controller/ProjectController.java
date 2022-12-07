package com.edddoubled.orunmila.controller;

import com.edddoubled.orunmila.model.Employee;
import com.edddoubled.orunmila.model.Project;
import com.edddoubled.orunmila.service.ProjectService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/project")
public class ProjectController {

    ProjectService projectService;

    @GetMapping("/get/{name}")
    public ResponseEntity<Project> getProjectByName(@PathVariable("name") String name) {
        Optional<Project> project = projectService.findProjectByName(name);
        if (project.isEmpty())  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(project.get(), HttpStatus.OK);
    }
}
