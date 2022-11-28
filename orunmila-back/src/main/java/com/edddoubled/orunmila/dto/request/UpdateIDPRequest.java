package com.edddoubled.orunmila.dto.request;

import com.edddoubled.orunmila.model.Employee;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UpdateIDPRequest implements Serializable {
    String login;

    String data;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Model implements Serializable {
        @SerializedName(value = "class")
        String clazz;

        List<Employee.Node> nodeDataArray;
    }
}
