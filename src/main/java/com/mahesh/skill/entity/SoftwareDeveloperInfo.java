package com.mahesh.skill.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "softwareDeveloperSkill")
public class SoftwareDeveloperInfo {
    @Id
    @JsonIgnore
    private ObjectId mongoId;

    @Indexed (unique = true)
    private long ssn;

    @Indexed
    private String firstName;

    @Indexed
    private String lastName;

    @Indexed
    private String emailAddress;

    @Indexed
    private String phoneNumber;

   @Indexed
    private SkillInfo primarySkillInfo;

   @Indexed
    private SkillInfo secondarySkillInfo;
}