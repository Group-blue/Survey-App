package com.bilgeadam.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveOptionRequestDto {
    private int orderNo;
    private String description;
}
