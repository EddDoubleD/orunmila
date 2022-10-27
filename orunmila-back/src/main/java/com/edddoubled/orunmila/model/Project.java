package com.edddoubled.orunmila.model;

import com.edddoubled.orunmila.service.ProjectService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@Document(collection = "project")
public class Project implements Serializable {

    @Id
    String id;

    @NotBlank
    @Size(max = 20)
    String name;

    @NotBlank
    @Size(max = 255)
    String description;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
