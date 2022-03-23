package com.bilgeadam.util;

import com.bilgeadam.dto.request.TeacherRequestDto;
import com.bilgeadam.dto.response.CourseBasicResponseDto;
import com.bilgeadam.dto.response.TeacherDetailResponseDto;
import com.bilgeadam.dto.response.TopicResponseDto;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Teacher;
import com.bilgeadam.repository.entity.Topic;

import java.util.Optional;

public class TeacherServiceUtils {

    public static Teacher getTeacher(){
        return Teacher.builder()
                .idNumber(11111111111L)
                .firstname("Teachertest")
                .lastname("Teachertest")
                .email("teachertest@bilgeadam.com")
                .password("Aa123456")
                .address("Istanbul")
                .province("Istanbul")
                .district("Istanbul")
                .phone1("5555555555")
                .phone2("5555555555")
                .yearsOfexperience(1)
                .build();
    }

    public static Teacher getTeacherWithId(){
        return Teacher.builder()
                .id(1)
                .idNumber(11111111111L)
                .firstname("Teachertest")
                .lastname("Teachertest")
                .email("teachertest@bilgeadam.com")
                .password("Aa123456")
                .address("Istanbul")
                .province("Istanbul")
                .district("Istanbul")
                .phone1("5555555555")
                .phone2("5555555555")
                .yearsOfexperience(1)
                .build();
    }

    public static TeacherRequestDto getTeacherRequestDto(){
        return TeacherRequestDto.builder()
                .idNumber(11111111111L)
                .firstname("Teachertest")
                .lastname("Teachertest")
                .email("teachertest@bilgeadam.com")
                .password("Aa123456")
                .address("Istanbul")
                .province("Istanbul")
                .district("Istanbul")
                .phone1("5555555555")
                .phone2("5555555555")
                .build();
    }

    public static CourseBasicResponseDto getCourseDto(){
        return CourseBasicResponseDto.builder().name("TestCourse").build();
    }

    public static Course getTeachersCourse(){
        return Course.builder().name("TestCourse").build();
    }

    public static TopicResponseDto getTopicDto(){
        return TopicResponseDto.builder().name("TestTopic").build();
    }

    public static Topic getTeachersTopic(){
        return Topic.builder().name("TestTopic").build();
    }
}
