package com.bilgeadam.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserRequestDto {
    private String email;
    private String password;
}
