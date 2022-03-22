package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveSurveyRequestDto;
import com.bilgeadam.dto.response.StudentAnswersResponseDto;
import com.bilgeadam.service.StudentAnswersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/studentanswers")
public class StudentAnswersController {

    StudentAnswersService studentAnswersService;

    public StudentAnswersController(StudentAnswersService studentAnswersService) {
        this.studentAnswersService = studentAnswersService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveStudentAnswers(@RequestBody StudentAnswersResponseDto dto) {
        studentAnswersService.save(dto);
        return ResponseEntity.ok().build();
    }
}
