package com.bilgeadam.repository.controller;

import com.bilgeadam.dto.request.SaveTemplateRequestDto;
import com.bilgeadam.service.PossibleAnswersService;
import com.bilgeadam.service.QuestionService;
import com.bilgeadam.service.SurveyTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/surveytemplate")
public class SurveyTemplateController {
    private final SurveyTemplateService surveyTemplateService;

    public SurveyTemplateController(SurveyTemplateService surveyTemplateService) {
        this.surveyTemplateService = surveyTemplateService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveTemplate(@RequestBody SaveTemplateRequestDto dto){
        surveyTemplateService.saveSurveyTemplate(dto);
        return ResponseEntity.ok().build();
    }
}
