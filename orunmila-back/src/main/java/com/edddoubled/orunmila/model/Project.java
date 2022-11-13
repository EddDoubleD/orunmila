package com.edddoubled.orunmila.model;

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
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
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

    /**
     * graph data
     */
    List<Node> data;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * Graph nodes, for storing flat information:
     * <pre>
     *     {@code { 'key': 15, 'name': 'MyNameIs?', 'title': 'job title', 'parent': 5 }}
     * </pre>
     */
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Node implements Serializable {
        /**
         * unique id of NodeList
         */
        int key;

        /**
         * employee name
         */
        String name;

        /**
         * employee position
         */
        String title;

        /**
         * id of parent node
         */
        int parent;
    }
}
