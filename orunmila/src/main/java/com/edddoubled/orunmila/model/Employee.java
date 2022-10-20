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

    Role role;

    List<String> projects;

    String position;

    String subdivision;

    List<Pair> skills;

    List<IDPNode> idpTemplate;


    @Getter
    @Setter
    @FieldDefaults(level= AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class IDPNode implements Serializable {
        int key;
        int parent;
        String text;
        String brush;
        String dir;
        String loc;
    }



    @Getter
    @Setter
    @FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
    @AllArgsConstructor
    public static class Pair implements Serializable {

        String key;

        String value;
    }
}
