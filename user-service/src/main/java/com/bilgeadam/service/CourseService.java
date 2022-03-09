package com.bilgeadam.service;

import com.bilgeadam.dto.request.BranchRequestDto;
import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.dto.request.TeacherRequestDto;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.ICourseRepository;
import com.bilgeadam.repository.IStudentRepository;
import com.bilgeadam.repository.entity.Branch;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CourseService {
    private final ICourseRepository courseRepository;
    private final UserServiceMapper mapper;

    public CourseService(ICourseRepository courseRepository, UserServiceMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }
    public Course save(CourseRequestDto dto) {
        Teacher masterTrainer=Teacher.builder().teacherId(dto.getMasterTrainer().getTeacherId()).build();
        Teacher assistantTrainer=Teacher.builder().teacherId(dto.getAssistantTrainer().getTeacherId()).build();
        Branch branch =Branch.builder().id(dto.getBranch().getId()).build();
        Course course=Course.builder().courseCode(dto.getCourseCode()).name(dto.getName())
                .startDate(dto.getStartDate()).endDate(dto.getEndDate()).masterTrainer(masterTrainer).
                assistantTrainer(assistantTrainer).branch(branch).build();

        return courseRepository.save(course);

//        private String courseCode;
//        private String name;
//        private long startDate;
//        private long endDate;
//        private TeacherRequestDto masterTrainer;
//        private TeacherRequestDto assistantTrainer;
//        private BranchRequestDto branch;

    }
}
