package com.mahesh.skill.repository;

import com.mahesh.skill.entity.SoftwareDeveloperInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository extends MongoRepository<SoftwareDeveloperInfo, String> {
    List<SoftwareDeveloperInfo> findAll();

    SoftwareDeveloperInfo findBySsn(Long ssn);
}

