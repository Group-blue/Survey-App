package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionDetailsResponseDto {
    private long id;
    private int orderNo;
    private String description;
}
