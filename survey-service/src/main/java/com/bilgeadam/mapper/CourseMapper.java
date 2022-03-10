package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.SaveOptionRequestDto;
import com.bilgeadam.dto.response.CourseBasicResponseDto;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.PossibleAnswers;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CourseMapper {

    CourseBasicResponseDto mapCoursetoDto(Course course);
}
