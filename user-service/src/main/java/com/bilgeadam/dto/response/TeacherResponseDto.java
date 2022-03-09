package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherResponseDto {
    private long id;
    private long teacherId;
    private long employeeId;
    private String firstname;
    private String lastname;
}
