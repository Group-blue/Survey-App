package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentSurveyResponseDto {
    private long studentId;
    private long surveyId;
    private long status;
    private TemplateDetailsResponseDto surveyTemplate;

}
