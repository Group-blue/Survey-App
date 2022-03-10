package com.bilgeadam.service;

import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.ICourseRepository;
import com.bilgeadam.repository.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    private final ICourseRepository courseRepository;
    private final UserServiceMapper mapper;

    public CourseService(ICourseRepository courseRepository, UserServiceMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }
    public Course save(CourseRequestDto dto) {
        Teacher masterTrainer=Teacher.builder().id(dto.getMasterTrainer().getId()).build();
        Teacher assistantTrainer=Teacher.builder().id(dto.getAssistantTrainer().getId()).build();
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

    public List<CourseResponseDto> listAllCourse(){
        List<Course> courseList=courseRepository.findAll();
        List<CourseResponseDto> courseResponseDtoList=new ArrayList<>();
        for (Course course:courseList) {
            CourseResponseDto courseResponseDto=CourseResponseDto.builder().id(course.getId())
                    .courseCode(course.getCourseCode()).name(course.getName()).startDate(course.getStartDate())
                    .endDate(course.getEndDate()).build();
            courseResponseDtoList.add(courseResponseDto);
        }
//        private long id;
//        private String courseCode;
//        private String name;
//        private long startDate;
//        private long endDate;
        return courseResponseDtoList;
    }

    public CourseDetailResponseDto getCourseDetailsById(long courseId) {
        Optional<Course> dbCourse=courseRepository.findById(courseId);
        if (dbCourse.isPresent()){
            Course course=dbCourse.get();
            TeacherResponseDto masterTrainerResponseDto=TeacherResponseDto.builder().id(course.getMasterTrainer().getId())
                    .firstname(course.getMasterTrainer().getFirstname()).lastname(course.getMasterTrainer().getLastname())
                    .employeeId(course.getMasterTrainer().getEmployeeId()).teacherId(course.getMasterTrainer().getTeacherId()).build();
            TeacherResponseDto assistantTrainerResponseDto=TeacherResponseDto.builder().id(course.getAssistantTrainer().getId())
                    .firstname(course.getAssistantTrainer().getFirstname()).lastname(course.getAssistantTrainer().getLastname())
                    .employeeId(course.getAssistantTrainer().getEmployeeId()).teacherId(course.getAssistantTrainer().getTeacherId()).build();
            BranchResponseDto branchResponseDto=BranchResponseDto.builder().id(course.getBranch().getId()).name(course.getBranch().getName()).build();
            List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();
            List<SurveyResponseDto> surveyResponseDtoList=new ArrayList<>();
            for (Student student:course.getStudents()) {
                StudentResponseDto dto =StudentResponseDto.builder().id(student.getId()).idNumber(student.getIdNumber())
                        .firstname(student.getFirstname()).lastname(student.getLastname()).province(student.getProvince())
                        .build();
                studentResponseDtoList.add(dto);
            }
            for (Survey survey:course.getSurveys()) {
                SurveyResponseDto dto=SurveyResponseDto.builder().id(survey.getId()).build();
                surveyResponseDtoList.add(dto);
            }
            CourseDetailResponseDto courseDetailResponseDto=CourseDetailResponseDto.builder().courseCode(course.getCourseCode())
                    .name(course.getName()).startDate(course.getStartDate()).endDate(course.getEndDate()).masterTrainer(masterTrainerResponseDto)
                    .assistantTrainer(assistantTrainerResponseDto).students(studentResponseDtoList).surveys(surveyResponseDtoList).id(course.getId())
                    .build();
            return courseDetailResponseDto;

        }else {
            return new CourseDetailResponseDto();
        }
    }

    public boolean updateCourse(CourseDetailResponseDto dto){
        Teacher masterTrainer=Teacher.builder().id(dto.getMasterTrainer().getId()).teacherId(dto.getMasterTrainer().getTeacherId())
                .employeeId(dto.getMasterTrainer().getEmployeeId()).build();
        Teacher assistantTrainer=Teacher.builder().id(dto.getAssistantTrainer().getId()).teacherId(dto.getAssistantTrainer().getTeacherId())
                .employeeId(dto.getAssistantTrainer().getEmployeeId()).build();
        Branch branch=Branch.builder().id(dto.getBranch().getId()).build();
        Set<Student>studentSet=new HashSet<>();
        Set<Survey>surveySet=new HashSet<>();
        for (StudentResponseDto studentResponseDto: dto.getStudents()) {
            Student student=Student.builder().id(studentResponseDto.getId()).idNumber(studentResponseDto.getIdNumber())
                    .firstname(studentResponseDto.getFirstname()).lastname(studentResponseDto.getLastname())
                    .province(studentResponseDto.getProvince()).build();
            studentSet.add(student);
        }
        for (SurveyResponseDto surveyResponseDto:dto.getSurveys()) {
            Survey survey=Survey.builder().id(surveyResponseDto.getId()).build();
            surveySet.add(survey);
        }
        Course course=Course.builder().id(dto.getId()).courseCode(dto.getCourseCode()).name(dto.getName())
                .startDate(dto.getStartDate()).endDate(dto.getEndDate()).masterTrainer(masterTrainer)
                .assistantTrainer(assistantTrainer).branch(branch).students(studentSet).surveys(surveySet).build();

        courseRepository.save(course);
        return true;
    }





}
//    private String courseCode;
//    private String name;
//    private long startDate;
//    private long endDate;
//    private TeacherResponseDto masterTrainer;
//    private TeacherResponseDto assistantTrainer;
//    private BranchResponseDto branch;
//    private List<StudentResponseDto> students;
//    private List<SurveyResponseDto> surveys;