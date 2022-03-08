package com.bilgeadam.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDetailResponseDto {
    private long id;
    private int orderNo;
    private String title;
    private String text;
    private int type;
    private boolean isOptional;

    private Set<OptionDetailsResponseDto> options;
}
