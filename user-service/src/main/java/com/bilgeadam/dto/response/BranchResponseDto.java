package com.bilgeadam.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseDto {
    private long id;
    private String name;
    private String address;
    private String province;
    private String district;
    private EmployeeResponseDto manager;

}
