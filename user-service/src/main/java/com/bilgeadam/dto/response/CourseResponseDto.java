package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponseDto {
    private long id;
    private String courseCode;
    private String name;
    private long startDate;
    private long endDate;
}
