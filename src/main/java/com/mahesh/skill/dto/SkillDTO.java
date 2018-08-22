package com.mahesh.skill.dto;

import com.mahesh.skill.entity.SkillInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SkillDTO {
    Long ssn;
    SkillInfo primarySkillInfo;
    SkillInfo secondarySkillInfo;
}
