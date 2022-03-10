package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseDto {
    private long id;
    private String name;
}
