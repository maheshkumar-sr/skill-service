package com.mahesh.skill.service;

import com.mahesh.skill.dto.SkillDTO;
import com.mahesh.skill.entity.SkillInfo;
import com.mahesh.skill.entity.SoftwareDeveloperInfo;
import com.mahesh.skill.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkillService {
    private final Logger logger = LoggerFactory.getLogger(SkillService.class);
    @Autowired
    private SkillRepository skillRepository;

    public ResponseEntity<Object> insertNewSkilledResources(Iterator<Map<String, String>> iterator) {
        while (iterator.hasNext()) {
            SoftwareDeveloperInfo softwareDeveloperInfo = new SoftwareDeveloperInfo();
            Map<String, String> keyVals = iterator.next();
            softwareDeveloperInfo.setFirstName(keyVals.get("\uFEFFfirstName") == null ? "" : keyVals.get("\uFEFFfirstName"));
            softwareDeveloperInfo.setLastName(keyVals.get("lastName") == null ? "" : keyVals.get("lastName"));
            softwareDeveloperInfo.setSsn(keyVals.get("ssn") == null ? 0 : (Long.valueOf(keyVals.get("ssn").replaceAll("[^0-9]", "")).longValue()));
            softwareDeveloperInfo.setEmailAddress(keyVals.get("emailAddress") == null ? "" : keyVals.get("emailAddress"));
            softwareDeveloperInfo.setPhoneNumber(keyVals.get("phoneNumber") == null ? "" : keyVals.get("phoneNumber").replaceAll("[^0-9]", ""));
            SkillInfo primarySkillInfo = new SkillInfo();
            primarySkillInfo.setSkill(keyVals.get("primarySkill") == null ? "" : keyVals.get("primarySkill"));
            primarySkillInfo.setExperienceInYears(keyVals.get("primarySkillExperienceInYears") == null ? 0 : (Long.valueOf(keyVals.get("primarySkillExperienceInYears").replaceAll("[^0-9]", "")).intValue()));
            primarySkillInfo.setSkillType(keyVals.get("primarySkillType") == null ? "" : keyVals.get("primarySkillType"));
            SkillInfo secondarySkillInfo = new SkillInfo();
            secondarySkillInfo.setSkill(keyVals.get("secondarySkill") == null ? "" : keyVals.get("secondarySkill"));
            secondarySkillInfo.setExperienceInYears(keyVals.get("secondarySkillExperienceInYears") == null ? 0 : (Long.valueOf(keyVals.get("secondarySkillExperienceInYears").replaceAll("[^0-9]", "")).intValue()));
            secondarySkillInfo.setSkillType(keyVals.get("secondarySkillType") == null ? "" : keyVals.get("secondarySkillType"));
            softwareDeveloperInfo.setPrimarySkillInfo(primarySkillInfo);
            softwareDeveloperInfo.setSecondarySkillInfo(secondarySkillInfo);
            skillRepository.save(softwareDeveloperInfo);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> addSkill(SkillDTO skillDTO) {
        SoftwareDeveloperInfo softwareDeveloperInfo = skillRepository.findBySsn(skillDTO.getSsn());
        if (null != softwareDeveloperInfo) {
            softwareDeveloperInfo.setPrimarySkillInfo(skillDTO.getPrimarySkillInfo());
            softwareDeveloperInfo.setSecondarySkillInfo(skillDTO.getSecondarySkillInfo());
            skillRepository.save(softwareDeveloperInfo);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<SoftwareDeveloperInfo> search(String search) {
        List<SoftwareDeveloperInfo> softwareDeveloperInfoList = skillRepository.findAll();

        List<SoftwareDeveloperInfo> searchResult = softwareDeveloperInfoList
                .stream()
                .filter(softwareDeveloperInfo -> softwareDeveloperInfo.getPrimarySkillInfo().getSkill().toLowerCase().contains(search.toLowerCase())
                        || softwareDeveloperInfo.getSecondarySkillInfo().getSkill().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
        return searchResult;
    }

    public List<SoftwareDeveloperInfo> searchAndFilter(String search, Integer minExperience, Integer maxExperience) {
        List<SoftwareDeveloperInfo> softwareDeveloperInfoList = skillRepository.findAll();

        List<SoftwareDeveloperInfo> searchResult = softwareDeveloperInfoList
                .stream()
                .filter(softwareDeveloperInfo -> (
                        (softwareDeveloperInfo.getPrimarySkillInfo().getSkill().toLowerCase().contains(search.toLowerCase())
                                && (softwareDeveloperInfo.getPrimarySkillInfo().getExperienceInYears() >= minExperience
                                && softwareDeveloperInfo.getPrimarySkillInfo().getExperienceInYears() <= maxExperience))
                                ||
                                (softwareDeveloperInfo.getSecondarySkillInfo().getSkill().toLowerCase().contains(search.toLowerCase())
                                        && (softwareDeveloperInfo.getSecondarySkillInfo().getExperienceInYears() >= minExperience
                                        && softwareDeveloperInfo.getSecondarySkillInfo().getExperienceInYears() <= maxExperience))
                        )
                )
                .collect(Collectors.toList());
        return searchResult;
    }
}
