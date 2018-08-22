package com.mahesh.skill.controller;


import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.mahesh.skill.dto.SkillDTO;
import com.mahesh.skill.entity.SoftwareDeveloperInfo;
import com.mahesh.skill.repository.SkillRepository;
import com.mahesh.skill.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final Logger logger = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping(path = "/importSkilledResources", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> importSkilledResources(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseEntity responseEntity = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            Iterator<Map<String, String>> iterator = new CsvMapper()
                    .readerFor(Map.class)
                    .with(CsvSchema.emptySchema().withHeader())
                    .readValues(bufferedReader);

            responseEntity = skillService.insertNewSkilledResources(iterator);
        } catch (Exception e) {
            logger.error("Error in importing skilled resources, {} - {}",e, e.getMessage());
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(path = "/addSkill", method = RequestMethod.POST)
    public ResponseEntity<Object> addSkill(@Valid @RequestBody SkillDTO skillDTO) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = skillService.addSkill(skillDTO);
        } catch (Exception e) {
            logger.error("Error in updating skill, {} - {}",e, e.getMessage());
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(path = "/searchSkill", method = RequestMethod.GET)
    public List<SoftwareDeveloperInfo> searchSkill(@Valid @RequestParam String search) {
        return skillService.search(search);
    }

    @RequestMapping(path = "/searchSkillByFilters", method = RequestMethod.GET)
    public List<SoftwareDeveloperInfo> searchSkill(@Valid @RequestParam String search,
                                                   @Valid @RequestParam Integer minExperience,
                                                   @Valid @RequestParam Integer maxExperience) {
        return skillService.searchAndFilter(search, minExperience, maxExperience);
    }
}
