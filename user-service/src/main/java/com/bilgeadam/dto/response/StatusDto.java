package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDto {
    /**
     * 200->OK
     * 500->Server Error
     */
    private int status;
}
