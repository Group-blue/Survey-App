package com.bilgeadam.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudentAnswersResponseDto {
    private long studentId;
    private long surveyId;
    private boolean finished;
    private List<StudentQuestionAnswerResponseDto> answers;
}
