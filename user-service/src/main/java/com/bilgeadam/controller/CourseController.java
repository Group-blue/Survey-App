package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/course")
public class CourseController {
private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveCourse(@RequestBody CourseRequestDto dto) {
        courseService.save(dto);
        return ResponseEntity.ok().build();
    }
}
