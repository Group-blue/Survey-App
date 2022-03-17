package com.bilgeadam.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateDetailsResponseDto {
    private long id;
    private String templateName;
    private long version;
    private String explanation;
    private long validityStartDate;
    private long validityEndDate;
    private boolean isDraft;

    private Set<QuestionDetailResponseDto> questions;
}
