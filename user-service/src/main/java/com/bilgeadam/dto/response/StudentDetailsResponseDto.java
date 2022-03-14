package com.bilgeadam.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDetailsResponseDto {
    private long id;
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
    private long registrationId;
    private long registrationDate;
    private List<CourseBasicResponseDto> courses;

}
