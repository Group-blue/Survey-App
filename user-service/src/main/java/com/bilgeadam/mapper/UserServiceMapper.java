package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.BranchRequestDto;
import com.bilgeadam.dto.request.CourseRequestDto;
import com.bilgeadam.dto.request.StudentRequestDto;
import com.bilgeadam.dto.request.TeacherRequestDto;
import com.bilgeadam.dto.response.EmployeeResponseDto;
import com.bilgeadam.dto.response.StudentResponseDto;
import com.bilgeadam.dto.response.TeacherResponseDto;
import com.bilgeadam.repository.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserServiceMapper {

    Student mapDtoToStudent(StudentRequestDto dto);

    StudentResponseDto mapStudentToResponseDto(Student student);

    Teacher mapDtoToTeacher(TeacherRequestDto dto);

    TeacherResponseDto mapTeacherToResponseDto(Teacher teacher);

    Branch mapDtoToBranch(BranchRequestDto dto);

    EmployeeResponseDto mapEmployeetoDto(Employee employee);

    Employee mapDtoToEmployee(EmployeeResponseDto dto);
}
