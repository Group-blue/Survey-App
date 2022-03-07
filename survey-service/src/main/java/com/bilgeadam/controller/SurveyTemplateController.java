package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveTemplateRequestDto;
import com.bilgeadam.dto.response.ListAllTemplateResponseDto;
import com.bilgeadam.service.SurveyTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
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

    @GetMapping("/listalltemplates")
    public ResponseEntity<List<ListAllTemplateResponseDto>> listAllTemplates(){
    return ResponseEntity.ok(surveyTemplateService.listAllTemplates());
    }

//    public ResponseEntity<dto> findTemplateById(@RequestParam long id){
//
//        return ResponseEntity.ok(dto);
//    }
}
