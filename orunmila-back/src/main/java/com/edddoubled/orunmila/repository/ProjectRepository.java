package com.edddoubled.orunmila.repository;

import com.edddoubled.orunmila.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
