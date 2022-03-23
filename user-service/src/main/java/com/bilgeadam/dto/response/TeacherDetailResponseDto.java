package com.bilgeadam.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDetailResponseDto {
    /**
     * 200 -> OK
     * 404 -> Not Found
     * 500 -> Server Error
     */
    private int status;
    private long id;
    private long teacherId;
    private long employeeId;
    private long idNumber;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String address;
    private String province;
    private String district;
    private String phone1;
    private String phone2;
    private int yearsOfexperience;
    List<CourseBasicResponseDto> masterCourses;
    List<CourseBasicResponseDto> assistantCourses;
    List<TopicResponseDto> topics;

}
