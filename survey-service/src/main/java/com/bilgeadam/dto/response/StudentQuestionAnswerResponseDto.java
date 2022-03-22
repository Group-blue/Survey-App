package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudentQuestionAnswerResponseDto {
    private int type;
    private long questionId;
    private String answer;
}
