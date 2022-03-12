package com.bilgeadam.service;

import com.bilgeadam.dto.request.TeacherRequestDto;
import com.bilgeadam.dto.response.CourseBasicResponseDto;
import com.bilgeadam.dto.response.TeacherDetailResponseDto;
import com.bilgeadam.dto.response.TeacherResponseDto;
import com.bilgeadam.dto.response.TopicResponseDto;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.ICourseRepository;
import com.bilgeadam.repository.ITeacherRepository;
import com.bilgeadam.repository.ITopicRepository;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Teacher;
import com.bilgeadam.repository.entity.Topic;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherService {
    ITeacherRepository teacherRepository;
    ICourseRepository courseRepository;
    ITopicRepository topicRepository;
    UserServiceMapper mapper;

    public TeacherService(ITeacherRepository teacherRepository, ICourseRepository courseRepository, ITopicRepository topicRepository, UserServiceMapper mapper) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.topicRepository = topicRepository;
        this.mapper = mapper;
    }

    public void save(TeacherRequestDto dto) {
        Teacher teacher = mapper.mapDtoToTeacher(dto);
        teacherRepository.save(teacher);

    }

    public List<TeacherResponseDto> listAllTeacher() {
        List<TeacherResponseDto> teacherResponseDtoList = new ArrayList<>();
        List<Teacher> teacherList = teacherRepository.findAll();
        for (Teacher teacher : teacherList) {
            TeacherResponseDto teacherResponseDto = mapper.mapTeacherToResponseDto(teacher);
            teacherResponseDtoList.add(teacherResponseDto);
        }
        return teacherResponseDtoList;
    }


    public TeacherDetailResponseDto getTeacherDetailById(long id) {
        Optional<Teacher> teacherDb = teacherRepository.findById(id);
        List<CourseBasicResponseDto> masterCourses = new ArrayList<>();
        List<CourseBasicResponseDto> assistantCourses = new ArrayList<>();
        List<TopicResponseDto> topics = new ArrayList<>();
        if (teacherDb.isPresent()) {
            Teacher teacher = teacherDb.get();
            for (Course masterCourse : teacher.getMasterCourses()
            ) {
                masterCourses.add(CourseBasicResponseDto.builder().id(masterCourse.getId()).name(masterCourse.getName()).build());

            }
            for (Course assistantCourse : teacher.getAssistanCourses()
            ) {
                assistantCourses.add(CourseBasicResponseDto.builder().id(assistantCourse.getId()).name(assistantCourse.getName()).build());

            }
            for (Topic topic : teacher.getTopics()
            ) {
                topics.add(TopicResponseDto.builder().id(topic.getId()).name(topic.getName()).topicCode(topic.getTopicCode())
                        .description(topic.getDescription()).build());
            }


            TeacherDetailResponseDto teacherDetailResponseDto = TeacherDetailResponseDto.builder().id(teacher.getId())
                    .teacherId(teacher.getTeacherId()).employeeId(teacher.getEmployeeId()).idNumber(teacher.getIdNumber())
                    .firstname(teacher.getFirstname()).lastname(teacher.getLastname()).address(teacher.getAddress())
                    .province(teacher.getProvince()).district(teacher.getDistrict()).phone1(teacher.getPhone1())
                    .phone2(teacher.getPhone2()).yearsOfexperience(teacher.getYearsOfexperience())
                    .masterCourses(masterCourses).assistantCourses(assistantCourses).topics(topics).build();
            return teacherDetailResponseDto;
        } else {
            return new TeacherDetailResponseDto();
        }
    }


    public boolean update(TeacherDetailResponseDto dto) {
        Set<Course>masterCourses=new HashSet<>();
        Set<Course>assistantCourses=new HashSet<>();
        Set<Topic>topics=new HashSet<>();

        for (CourseBasicResponseDto courseBasicResponseDto:dto.getMasterCourses()
             ) {
            Course course=courseRepository.findById(courseBasicResponseDto.getId()).get();
            masterCourses.add(course);
        }
        for (CourseBasicResponseDto courseBasicResponseDto:dto.getAssistantCourses()
             ) {
            Course course=courseRepository.findById(courseBasicResponseDto.getId()).get();
            assistantCourses.add(course);
        }
        for (TopicResponseDto topicResponseDto:dto.getTopics()
             ) {
            Topic topic=topicRepository.findById(topicResponseDto.getId()).get();
            topics.add(topic);
        }

        Teacher teacher=Teacher.builder().id(dto.getId()).teacherId(dto.getId()).employeeId(dto.getTeacherId())
                .idNumber(dto.getIdNumber()).firstname(dto.getFirstname()).lastname(dto.getLastname())
                .address(dto.getAddress()).province(dto.getProvince()).district(dto.getDistrict()).phone1(dto.getPhone1())
                .phone2(dto.getPhone2()).yearsOfexperience(dto.getYearsOfexperience()).masterCourses(masterCourses)
                .assistanCourses(assistantCourses).topics(topics).build();
       Teacher teacherDb=teacherRepository.save(teacher);

        for (CourseBasicResponseDto courseBasicResponseDto:dto.getMasterCourses()
        ) {
            Course course=courseRepository.findById(courseBasicResponseDto.getId()).get();
            course.setMasterTrainer(teacherDb);
            courseRepository.save(course);
        }
        for (CourseBasicResponseDto courseBasicResponseDto:dto.getAssistantCourses()
        ) {
            Course course=courseRepository.findById(courseBasicResponseDto.getId()).get();
            course.setAssistantTrainer(teacherDb);
            courseRepository.save(course);
        }
        for (TopicResponseDto topicResponseDto:dto.getTopics()
        ) {
            Topic topic=topicRepository.findById(topicResponseDto.getId()).get();
            topic.getTeachers().add(teacherDb);
            topicRepository.save(topic);
        }

        return true;
    }
}
//    private long id;
//    private long teacherId;
//    private long employeeId;
//    private long idNumber;
//    private String firstname;
//    private String lastname;
//    private String address;
//    private String province;
//    private String district;
//    private String phone1;
//    private String phone2;
//    private int yearsOfexperience;
//    List<CourseBasicResponseDto> masterCourses;
//    List<CourseBasicResponseDto> assistantCourses;
//    List<TopicResponseDto> topics;