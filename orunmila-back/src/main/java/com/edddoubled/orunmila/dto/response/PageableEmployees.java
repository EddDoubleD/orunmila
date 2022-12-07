package com.edddoubled.orunmila.dto.response;

import com.edddoubled.orunmila.model.Employee;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PageableEmployees implements Serializable {
    int page;

    int totalPages;

    long itemsSize;

    List<Employee> employees;
}
