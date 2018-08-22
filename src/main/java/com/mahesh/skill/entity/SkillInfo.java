package com.mahesh.skill.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class SkillInfo {

    @Id
    @JsonIgnore
    private ObjectId mongoId;

    @Indexed
    private String skill;

    @Indexed
    private Integer experienceInYears;

    @Indexed
    private String skillType;
}
