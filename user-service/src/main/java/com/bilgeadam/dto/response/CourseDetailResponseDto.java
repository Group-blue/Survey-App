package com.bilgeadam.dto.response;

import com.bilgeadam.repository.entity.Branch;
import com.bilgeadam.repository.entity.Student;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.repository.entity.Teacher;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDetailResponseDto {
    private long id;
    private String courseCode;
    private String name;
    private long startDate;
    private long endDate;
    private TeacherResponseDto masterTrainer;
    private TeacherResponseDto assistantTrainer;
    private BranchResponseDto branch;
    private List<StudentResponseDto> students;
    private List<SurveyResponseDto> surveys;
}
