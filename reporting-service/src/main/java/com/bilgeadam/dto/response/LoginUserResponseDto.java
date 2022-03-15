package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserResponseDto {
    private String token;

    /**
     * 200 -> Ok
     * 404 -> User Not Found
     * 500 -> Server Error
     */
    private int status;
}
