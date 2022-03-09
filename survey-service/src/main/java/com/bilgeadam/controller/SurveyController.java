package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveSurveyRequestDto;
import com.bilgeadam.dto.response.ListAllSurveyResponseDto;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> saveSurvey(@RequestBody SaveSurveyRequestDto dto){
        return ResponseEntity.ok(surveyService.saveSurvey(dto));
    }

    @GetMapping("/listallsurveys")
    public ResponseEntity<List<ListAllSurveyResponseDto>> listAllSurveys(){
        return ResponseEntity.ok(surveyService.listAllSurveys());
    }

//    @GetMapping("/findsurveybyid")
//    public ResponseEntity<SurveyDetailResponseDto> findSurveyById(@RequestParam long id){
//        return ResponseEntity.ok(surveyService.findById(id));
//    }
}
