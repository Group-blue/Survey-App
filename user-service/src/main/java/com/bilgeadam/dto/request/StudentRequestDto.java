package com.bilgeadam.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequestDto {
    private long idNumber;
    private String firstname;
    private String lastname;
    private String address;
    private String province;
    private String district;
    private String phone1;
    private String phone2;


}
