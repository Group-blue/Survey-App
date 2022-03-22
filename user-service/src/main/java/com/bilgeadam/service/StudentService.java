package com.bilgeadam.service;

import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.dto.response.CourseBasicResponseDto;
import com.bilgeadam.dto.response.StudentDetailsResponseDto;
import com.bilgeadam.dto.response.StudentResponseDto;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.rabbitmq.model.MailNotification;
import com.bilgeadam.repository.ICourseRepository;
import com.bilgeadam.repository.IStudentRepository;
import com.bilgeadam.repository.ISurveyRepository;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.util.JwtSurveyTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class StudentService {
    private final IStudentRepository studentRepository;
    private final UserServiceMapper mapper;
    private final ICourseRepository courseRepository;
    private final JwtSurveyTokenManager jwtSurveyTokenManager;
    private final ISurveyRepository surveyRepository;
    EmailSenderService emailSenderService;

    public StudentService(IStudentRepository studentRepository, UserServiceMapper mapper, ICourseRepository courseRepository, JwtSurveyTokenManager jwtSurveyTokenManager, ISurveyRepository surveyRepository, EmailSenderService emailSenderService) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
        this.courseRepository = courseRepository;
        this.jwtSurveyTokenManager = jwtSurveyTokenManager;
        this.surveyRepository = surveyRepository;
        this.emailSenderService = emailSenderService;
    }

    public Student save(StudentRequestDto studentRequestDto) {
        Student student = mapper.mapDtoToStudent(studentRequestDto);
        student.setRegistrationDate(new Date().getTime());
        return studentRepository.save(student);

    }

    public List<StudentResponseDto> listAllStudent() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponseDto> studentResponseDtoList = new ArrayList<>();

        for (Student student : studentList) {
            StudentResponseDto dto = mapper.mapStudentToResponseDto(student);
            studentResponseDtoList.add(dto);

        }
        return studentResponseDtoList;
    }

    public boolean updateStudent(StudentDetailsResponseDto dto) {
        Set<Course> courses = new HashSet<>();

        for (CourseBasicResponseDto courseBasicResponseDto : dto.getCourses()) {
            Course course=courseRepository.findById(courseBasicResponseDto.getId()).get();
            courses.add(course);

        }
       Student student= Student.builder().id(dto.getId()).idNumber(dto.getIdNumber()).firstname(dto.getFirstname())
                .lastname(dto.getLastname()).address(dto.getAddress()).province(dto.getProvince())
                .district(dto.getDistrict()).phone1(dto.getPhone1()).phone2(dto.getPhone2())
                .courses(courses).email(dto.getEmail()).password(dto.getPassword()).build();
        Student student1=studentRepository.save(student);
        for (CourseBasicResponseDto courseBasicResponseDto : dto.getCourses()) {
            Course course=courseRepository.findById(courseBasicResponseDto.getId()).get();
            course.getStudents().add(student1);
            courseRepository.save(course);

        }
        return true;





    }


    public StudentDetailsResponseDto getStudentDetailsById(long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        List<CourseBasicResponseDto> courseBasicResponseDtoList = new ArrayList<>();
        CourseBasicResponseDto courseBasicResponseDto = new CourseBasicResponseDto();
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            for (Course course : student.getCourses()) {
                courseBasicResponseDto.setId(course.getId());
                courseBasicResponseDto.setName(course.getName());
                courseBasicResponseDtoList.add(courseBasicResponseDto);
            }
            StudentDetailsResponseDto studentDetailsResponseDto =
                    StudentDetailsResponseDto.builder().id(student.getId()).idNumber(student.getIdNumber()).
                            firstname(student.getFirstname()).lastname(student.getLastname()).
                            address(student.getAddress()).province(student.getProvince()).
                            district(student.getDistrict()).phone1(student.getPhone1()).phone2(student.getPhone2())
                            .registrationId(student.getRegistrationId()).registrationDate(student.getRegistrationDate())
                            .courses(courseBasicResponseDtoList).email(student.getEmail()).build();
            return studentDetailsResponseDto;
        } else {

            return new StudentDetailsResponseDto();
        }
    }

    @Transactional
    public void createTokensAndMailToUsers(MailNotification notification){
        long courseId = notification.getCourseId();
        long surveyId = notification.getSurveyId();
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        if (survey.isPresent()){
            long expireAfter = survey.get().getEndDate();
            Optional<Course> course = courseRepository.findById(courseId);
            if(course.isPresent()) {
                Set<Student> students = course.get().getStudents();
                for (Student student : students) {
                    long id = student.getId();
                    String studentEmail=student.getEmail();
                    String token=jwtSurveyTokenManager.createToken(id,surveyId,expireAfter).get();
                    emailSenderService.sendEmail(studentEmail,token);
                }
            }
        }

        // gelen kurs id sine göre o kursa kayıtlı öğrenciler listesini çekecek
        // survey id notification içinde var
        // her öğrenci için içinde surveyid ve öğrenci id olan bir token üretecek
        // şimdilik loglara basacak bu tokenları.
    }
}
