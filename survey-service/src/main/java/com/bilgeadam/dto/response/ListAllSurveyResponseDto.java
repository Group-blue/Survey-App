package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ListAllSurveyResponseDto {

    private long id;
    private long templateId;
    private String templateName;
    private String templateExplanation;
    private long startDate;
    private long endDate;


}
