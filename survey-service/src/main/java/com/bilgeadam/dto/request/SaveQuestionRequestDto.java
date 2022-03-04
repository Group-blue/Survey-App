package com.bilgeadam.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveQuestionRequestDto {
    private int orderNo;
    private String title;
    private String text;
    private String type;
    private boolean isOptional;
    private List<SaveOptionRequestDto> options;
}
