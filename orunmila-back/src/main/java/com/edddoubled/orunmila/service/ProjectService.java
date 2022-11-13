package com.edddoubled.orunmila.service;

import com.edddoubled.orunmila.model.Project;
import com.edddoubled.orunmila.repository.ProjectRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
@Slf4j
public class ProjectService {

    ProjectRepository projectRepository;

    public Optional<Project> findProjectByName(String name) {
        return projectRepository.findProjectByName(name);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> saveAll(Collection<Project> projects) {
        return projectRepository.saveAll(projects);
    }

    public void removeAll() {
        projectRepository.deleteAll();
        log.info("all projects have been removed");
    }



}
