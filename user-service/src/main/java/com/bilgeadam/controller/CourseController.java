package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.dto.response.CourseDetailResponseDto;
import com.bilgeadam.dto.response.CourseResponseDto;
import com.bilgeadam.dto.response.StudentDetailsResponseDto;
import com.bilgeadam.dto.response.StudentResponseDto;
import com.bilgeadam.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/listall")
    public ResponseEntity<List<CourseResponseDto>> listAllStudent() {
        return ResponseEntity.ok(courseService.listAllCourse());
    }
    @GetMapping("/findcoursebyid")
    public ResponseEntity<CourseDetailResponseDto> findCourseById(@RequestParam long id) {
        return ResponseEntity.ok(courseService.getCourseDetailsById(id));
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateCourse(@RequestBody CourseDetailResponseDto dto) {
        return ResponseEntity.ok(courseService.updateCourse(dto));
    }
}
