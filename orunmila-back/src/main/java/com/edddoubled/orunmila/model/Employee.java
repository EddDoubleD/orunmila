package com.edddoubled.orunmila.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@Document(collection = "employee")
public class Employee implements Serializable {

    @Id
    String id;

    @NotBlank
    @Size(max = 255)
    String name;

    @Size(max = 40)
    String login;

    @NotBlank
    @Size(max = 50)
    @Email
    String email;

    String skype;

    String github;

    List<String> projects;

    String position;

    String subdivision;

    List<SkillBadge> skills;

    List<Node> data;


    @Getter
    @Setter
    @FieldDefaults(level= AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Node implements Serializable {
        int key;
        int parent;
        String text;
        String brush;
        String dir;
        String loc;
    }
}
