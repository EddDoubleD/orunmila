package com.edddoubled.orunmila.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
public class SkillBadge implements Serializable{

    String header;

    List<SkillProgress> skills;


    @Getter
    @Setter
    @FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
    @AllArgsConstructor
    public static class SkillProgress implements Serializable {

        String title;

        int progress;
    }
}
