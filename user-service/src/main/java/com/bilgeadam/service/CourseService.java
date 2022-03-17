package com.bilgeadam.service;

import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.*;
import com.bilgeadam.repository.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    private final ICourseRepository courseRepository;
    private final ITeacherRepository teacherRepository;
    private final IBranchRepository branchRepository;
    private final IStudentRepository studentRepository;
    private final ISurveyRepository surveyRepository;
    private final BranchService branchService;
    private final UserServiceMapper mapper;

    public CourseService(ICourseRepository courseRepository, ITeacherRepository teacherRepository, IBranchRepository branchRepository, IStudentRepository studentRepository, ISurveyRepository surveyRepository, BranchService branchService, UserServiceMapper mapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.branchRepository = branchRepository;
        this.studentRepository = studentRepository;
        this.surveyRepository = surveyRepository;
        this.branchService = branchService;
        this.mapper = mapper;
    }

    public Course save(CourseRequestDto dto) {
        Teacher masterTrainer=teacherRepository.findById(dto.getMasterTrainer().getId()).get();
        Teacher assistantTrainer=teacherRepository.findById(dto.getAssistantTrainer().getId()).get();
        Branch branch =branchRepository.findById(dto.getBranch().getId()).get();
        Course course=Course.builder().courseCode(dto.getCourseCode()).name(dto.getName())
                .startDate(dto.getStartDate()).endDate(dto.getEndDate()).masterTrainer(masterTrainer).
                assistantTrainer(assistantTrainer).branch(branch).build();
        Course courseDb=courseRepository.save(course);

        return courseDb;
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
                    .employeeId(course.getMasterTrainer().getEmployeeId()).teacherId(course.getMasterTrainer().getTeacherId()).
                    email(course.getMasterTrainer().getEmail()).build();
            TeacherResponseDto assistantTrainerResponseDto=TeacherResponseDto.builder().id(course.getAssistantTrainer().getId())
                    .firstname(course.getAssistantTrainer().getFirstname()).lastname(course.getAssistantTrainer().getLastname())
                    .employeeId(course.getAssistantTrainer().getEmployeeId()).teacherId(course.getAssistantTrainer().getTeacherId()).
                    email(course.getAssistantTrainer().getEmail()).build();
            BranchResponseDto branchResponseDto=branchService.getBranchDetailById(course.getBranch().getId());
            List<StudentResponseDto> studentResponseDtoList=new ArrayList<>();
            List<SurveyResponseDto> surveyResponseDtoList=new ArrayList<>();
            for (Student student:course.getStudents()) {
                StudentResponseDto dto =StudentResponseDto.builder().id(student.getId()).idNumber(student.getIdNumber())
                        .firstname(student.getFirstname()).lastname(student.getLastname()).province(student.getProvince())
                        .email(student.getEmail()).build();
                studentResponseDtoList.add(dto);
            }
            for (Survey survey:course.getSurveys()) {
                SurveyResponseDto dto=SurveyResponseDto.builder().id(survey.getId()).build();
                surveyResponseDtoList.add(dto);
            }
            CourseDetailResponseDto courseDetailResponseDto=CourseDetailResponseDto.builder().courseCode(course.getCourseCode())
                    .name(course.getName()).startDate(course.getStartDate()).endDate(course.getEndDate()).masterTrainer(masterTrainerResponseDto)
                    .assistantTrainer(assistantTrainerResponseDto).students(studentResponseDtoList).surveys(surveyResponseDtoList).id(course.getId())
                    .branch(branchResponseDto).build();
            return courseDetailResponseDto;

        }else {
            return new CourseDetailResponseDto();
        }
    }

    public boolean updateCourse(CourseDetailResponseDto dto){
        Teacher masterTrainer=teacherRepository.findById(dto.getMasterTrainer().getId()).get();
        Teacher assistantTrainer=teacherRepository.findById(dto.getAssistantTrainer().getId()).get();
        Branch branch =branchRepository.findById(dto.getBranch().getId()).get();
        Set<Student>studentSet=new HashSet<>();
        Set<Survey>surveySet=new HashSet<>();
        for (StudentResponseDto studentResponseDto: dto.getStudents()) {
            Student student=studentRepository.findById(studentResponseDto.getId()).get();
            studentSet.add(student);
        }
        for (SurveyResponseDto surveyResponseDto:dto.getSurveys()) {
            Survey survey=surveyRepository.findById(surveyResponseDto.getId()).get();
            surveySet.add(survey);
        }
        Course course=Course.builder().id(dto.getId()).courseCode(dto.getCourseCode()).name(dto.getName())
                .startDate(dto.getStartDate()).endDate(dto.getEndDate()).masterTrainer(masterTrainer)
                .assistantTrainer(assistantTrainer).branch(branch).students(studentSet).surveys(surveySet).build();

        Course courseDb=courseRepository.save(course);
        for (StudentResponseDto studentResponseDto: dto.getStudents()) {
            Student student=studentRepository.findById(studentResponseDto.getId()).get();
            student.getCourses().add(courseDb);
            studentRepository.save(student);
        }

        for (SurveyResponseDto surveyResponseDto:dto.getSurveys()) {
            Survey survey=surveyRepository.findById(surveyResponseDto.getId()).get();
            survey.setCourse(courseDb);
            surveyRepository.save(survey);
        }
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