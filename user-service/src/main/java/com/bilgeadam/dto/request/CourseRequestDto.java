package com.bilgeadam.dto.request;

import com.bilgeadam.repository.entity.Branch;
import com.bilgeadam.repository.entity.Teacher;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequestDto {

    private String courseCode;
    private String name;
    private long startDate;
    private long endDate;
    private TeacherRequestDto masterTrainer;
    private TeacherRequestDto assistantTrainer;
    private BranchRequestDto branch;

}
