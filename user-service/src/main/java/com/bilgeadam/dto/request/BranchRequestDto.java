package com.bilgeadam.dto.request;

import com.bilgeadam.repository.entity.Employee;
import lombok.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchRequestDto {

    private long id;
    private String name;
    private String address;
    private String province;
    private String district;

}
