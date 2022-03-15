package com.bilgeadam.controller;

import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.dto.response.StudentDetailsResponseDto;
import com.bilgeadam.dto.response.StudentResponseDto;
import com.bilgeadam.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveStudent(@RequestBody StudentRequestDto dto) {
        studentService.save(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listall")
    public ResponseEntity<List<StudentResponseDto>> listAllStudent() {
        return ResponseEntity.ok(studentService.listAllStudent());
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateStudent(@RequestBody StudentDetailsResponseDto dto) {
        return ResponseEntity.ok(studentService.updateStudent(dto));
    }

    @GetMapping("/findstudentbyid")
    public ResponseEntity<StudentDetailsResponseDto> findTemplateById(@RequestParam long id) {
        return ResponseEntity.ok(studentService.getStudentDetailsById(id));
    }
}
