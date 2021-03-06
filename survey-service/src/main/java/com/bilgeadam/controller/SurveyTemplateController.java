package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveTemplateRequestDto;
import com.bilgeadam.dto.response.ListAllTemplateResponseDto;
import com.bilgeadam.dto.response.TemplateDetailsResponseDto;
import com.bilgeadam.service.SurveyTemplateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping("/findtemplatebyid")
    public ResponseEntity<TemplateDetailsResponseDto> findTemplateById(@RequestParam long id){
        return ResponseEntity.ok(surveyTemplateService.getTemplateDetailsById(id));
    }

    @PostMapping("/updatetemplate")
    public ResponseEntity<Boolean> updateTemplate(@RequestBody TemplateDetailsResponseDto dto){
        return ResponseEntity.ok(surveyTemplateService.updateTemplate(dto));
    }

}
