package com.bilgeadam.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicResponseDto {
    private long id;
    private String topicCode;
    private String name;
    private String description;
}
