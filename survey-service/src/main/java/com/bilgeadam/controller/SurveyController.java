package com.bilgeadam.controller;

import com.bilgeadam.config.security.JwtTokenManager;
import com.bilgeadam.dto.request.SaveSurveyRequestDto;
import com.bilgeadam.dto.response.ListAllSurveyResponseDto;
import com.bilgeadam.dto.response.SurveyDetailResponseDto;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.service.SurveyService;
import com.bilgeadam.util.JwtSurveyTokenManager;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;



    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> saveSurvey(@RequestBody SaveSurveyRequestDto dto) {
        long surveyId = surveyService.saveSurvey(dto);
        if (surveyId > 0) {
            surveyService.sendMailToUsers(dto.getCourseId(), surveyId);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/getsurveybytoken")
    public ResponseEntity<Void> getStudentSurveyByToken(@RequestParam String token) {
        surveyService.getStudentSurveyByToken(token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/listallsurveys")
    public ResponseEntity<List<ListAllSurveyResponseDto>> listAllSurveys() {
        return ResponseEntity.ok(surveyService.listAllSurveys());
    }

    @GetMapping("/findsurveybyid")
    public ResponseEntity<SurveyDetailResponseDto> findSurveyById(@RequestParam long id) {
        return ResponseEntity.ok(surveyService.findSurveyById(id));
    }
}
