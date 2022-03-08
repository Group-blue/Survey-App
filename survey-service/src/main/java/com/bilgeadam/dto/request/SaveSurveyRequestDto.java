package com.bilgeadam.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SaveSurveyRequestDto {
    private long id;
    private long sequenceNumber;
    private long startDate;
    private long endDate;
    private long courseId;
    private long templateId;

}
