package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CourseBasicResponseDto {
    private long id;
    private String courseCode;
}
