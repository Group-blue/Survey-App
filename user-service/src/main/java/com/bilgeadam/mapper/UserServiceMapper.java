package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.dto.response.StudentResponseDto;
import com.bilgeadam.repository.entity.Course;
import com.bilgeadam.repository.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserServiceMapper {

    Student mapDtoToStudent(StudentRequestDto dto);
    StudentResponseDto mapStudentToResponseDto(Student student);

}
