package com.bilgeadam.service;

import com.bilgeadam.dto.request.TeacherRequestDto;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.mapper.UserServiceMapper;
import com.bilgeadam.repository.ICourseRepository;
import com.bilgeadam.repository.ITeacherRepository;
import com.bilgeadam.repository.ITopicRepository;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Teacher;
import com.bilgeadam.repository.entity.Topic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Teacher save(TeacherRequestDto dto) {
        Teacher teacher = mapper.mapDtoToTeacher(dto);
        return teacherRepository.save(teacher);
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

    @Transactional
    public TeacherDetailResponseDto getTeacherDetailById(long id) {
        Optional<Teacher> teacherDb = teacherRepository.findById(id);
        List<CourseBasicResponseDto> masterCourses = new ArrayList<>();
        List<CourseBasicResponseDto> assistantCourses = new ArrayList<>();
        List<TopicResponseDto> topics = new ArrayList<>();
        if (teacherDb.isPresent()) {
            Teacher teacher = teacherDb.get();
            for (Course masterCourse : teacher.getMasterCourses()) {
                masterCourses.add(CourseBasicResponseDto.builder().id(masterCourse.getId()).name(masterCourse.getName()).build());

            }
            for (Course assistantCourse : teacher.getAssistanCourses()) {
                assistantCourses.add(CourseBasicResponseDto.builder().id(assistantCourse.getId()).name(assistantCourse.getName()).build());

            }
            for (Topic topic : teacher.getTopics()) {
                topics.add(TopicResponseDto.builder().id(topic.getId()).name(topic.getName()).topicCode(topic.getTopicCode())
                        .description(topic.getDescription()).build());
            }

            TeacherDetailResponseDto teacherDetailResponseDto = TeacherDetailResponseDto.builder().id(teacher.getId())
                    .teacherId(teacher.getTeacherId()).employeeId(teacher.getEmployeeId()).idNumber(teacher.getIdNumber())
                    .firstname(teacher.getFirstname()).lastname(teacher.getLastname()).address(teacher.getAddress())
                    .province(teacher.getProvince()).district(teacher.getDistrict()).phone1(teacher.getPhone1())
                    .phone2(teacher.getPhone2()).yearsOfexperience(teacher.getYearsOfexperience())
                    .masterCourses(masterCourses).assistantCourses(assistantCourses).topics(topics).email(teacher.getEmail()).status(200).build();
            return teacherDetailResponseDto;
        } else {
            return TeacherDetailResponseDto.builder().status(404).build();
        }
    }

    public Set<Course> getCoursesByDto(List<CourseBasicResponseDto> courseDtoList) {
        if (courseDtoList.size()<1) return new HashSet<Course>();

        Set<Course> courses = new HashSet<>();

        for (CourseBasicResponseDto courseBasicResponseDto : courseDtoList) {
            Optional<Course> courseOptional = courseRepository.findById(courseBasicResponseDto.getId());
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                courses.add(course);
            }
        }

        return courses;
    }

    public Set<Topic> getTopicsByDto(List<TopicResponseDto> topicDtoList) {
        if (topicDtoList.size()<1) return new HashSet<Topic>();

        Set<Topic> topics = new HashSet<>();

        for (TopicResponseDto topicResponseDto : topicDtoList) {
            Optional<Topic> topicOptional = topicRepository.findById(topicResponseDto.getId());
            if (topicOptional.isPresent()) {
                Topic topic = topicOptional.get();
                topics.add(topic);
            }
        }

        return topics;
    }

    public void updateCoursesMasterTrainer(Set<Course> courses, Teacher teacher) {
        if (courses.size()>0){
            for (Course course : courses) {
                course.setMasterTrainer(teacher);
                courseRepository.save(course);
            }
        }
    }

    public void updateCoursesAssistantTrainer(Set<Course> courses, Teacher teacher) {
        if (courses.size()>0){
            for (Course course : courses) {
                course.setAssistantTrainer(teacher);
                courseRepository.save(course);
            }
        }
    }

    public void updateTopicsTeacher(Set<Topic> topics, Teacher teacher) {
        if (topics.size()>0){
            for (Topic topic : topics) {
                topic.getTeachers().add(teacher);
                topicRepository.save(topic);
            }
        }
    }

    @Transactional
    public StatusDto update(TeacherDetailResponseDto dto) {
        Set<Course> masterCourses = getCoursesByDto(dto.getMasterCourses());
        Set<Course> assistantCourses = getCoursesByDto(dto.getAssistantCourses());
        Set<Topic> topics = getTopicsByDto(dto.getTopics());


        Teacher teacher = Teacher.builder().id(dto.getId()).teacherId(dto.getId()).employeeId(dto.getTeacherId())
                .idNumber(dto.getIdNumber()).firstname(dto.getFirstname()).lastname(dto.getLastname())
                .address(dto.getAddress()).province(dto.getProvince()).district(dto.getDistrict()).phone1(dto.getPhone1())
                .phone2(dto.getPhone2()).yearsOfexperience(dto.getYearsOfexperience()).masterCourses(masterCourses)
                .assistanCourses(assistantCourses).topics(topics).build();
        Teacher teacherDb = teacherRepository.save(teacher);

        updateCoursesMasterTrainer(masterCourses, teacherDb);
        updateCoursesAssistantTrainer(assistantCourses, teacherDb);
        updateTopicsTeacher(topics, teacherDb);

        return StatusDto.builder().status(200).build();
    }


}