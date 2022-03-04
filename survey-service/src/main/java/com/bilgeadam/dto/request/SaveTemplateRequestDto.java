package com.bilgeadam.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveTemplateRequestDto {
    private boolean isDraft;
    private String templateName;
    private String explanation;

    private List<SaveQuestionRequestDto> questions;
}
