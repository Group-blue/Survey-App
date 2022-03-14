package com.bilgeadam.dto.response;

import com.bilgeadam.repository.entity.Course;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SurveyDetailResponseDto {

private long templateId;
private long sequenceNumber;
private long startDate;
private long endDate;
private CourseBasicResponseDto courseBasicResponseDto;

}
