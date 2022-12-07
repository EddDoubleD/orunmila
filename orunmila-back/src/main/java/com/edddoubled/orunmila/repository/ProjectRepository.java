package com.edddoubled.orunmila.repository;

import com.edddoubled.orunmila.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<Project> findProjectByName(String name);
}
