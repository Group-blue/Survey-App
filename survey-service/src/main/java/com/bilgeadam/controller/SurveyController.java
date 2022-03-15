package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveSurveyRequestDto;
import com.bilgeadam.dto.response.ListAllSurveyResponseDto;
import com.bilgeadam.dto.response.SurveyDetailResponseDto;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.service.SurveyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Boolean> saveSurvey(@RequestBody SaveSurveyRequestDto dto){
        long surveyId = surveyService.saveSurvey(dto);
        if (surveyId>0){
            surveyService.sendMailToUsers(dto.getCourseId(), surveyId);
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/getsurveybytoken")
    public ResponseEntity<Void> getStudentSurveyByToken(@RequestParam String token){
        // token valid mi kontrolü yapılacak ?
        // alınan tokenın içindeki studentid ve survey id ayıklanacak
        // ilgili id ye sahip survey öğrenciye atanmış mı kontrolü yapılacak ?
        // => survey hangi kursa atanmış => student id li öğrenc, bu kursa kayıtlıysa ok bu surveyi görebilir.
        // kullanıcıya survey detayları dönülecek (studentId, surveyId, templateId, sorular(idleri ile birlikte) ve optionlar(idleri ile birlikte) )
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listallsurveys")
    public ResponseEntity<List<ListAllSurveyResponseDto>> listAllSurveys(){
        return ResponseEntity.ok(surveyService.listAllSurveys());
    }

    @GetMapping("/findsurveybyid")
    public ResponseEntity<SurveyDetailResponseDto> findSurveyById(@RequestParam long id){
        return ResponseEntity.ok(surveyService.findSurveyById(id));
    }
}
