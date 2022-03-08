package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ListAllTemplateResponseDto {

    private long id;
    private boolean isDraft;
    private String templateName;
    private long validityStartDate;

}
