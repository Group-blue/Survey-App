package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDto {
    private long id;
    private long idNumber;
    private String firstname;
    private String lastname;
    private String province;


}
