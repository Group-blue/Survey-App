package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TeacherRequestDto;
import com.bilgeadam.dto.response.TeacherDetailResponseDto;
import com.bilgeadam.dto.response.TeacherResponseDto;
import com.bilgeadam.service.TeacherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody TeacherRequestDto teacherRequestDto) {
        teacherService.save(teacherRequestDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/listall")
    public ResponseEntity<List<TeacherResponseDto>> listAllTeacher(){
        return ResponseEntity.ok(teacherService.listAllTeacher());
    }
    @PostMapping("/findteacherbyid")
    public ResponseEntity<TeacherDetailResponseDto> findByTeacherId(@RequestParam long id){
        return ResponseEntity.ok(teacherService.getTeacherDetailById(id));
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> update(TeacherDetailResponseDto dto){
        return ResponseEntity.ok(teacherService.update(dto));
    }
}
