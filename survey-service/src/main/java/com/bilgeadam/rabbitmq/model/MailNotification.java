package com.bilgeadam.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailNotification implements Serializable {
    private static final long serialVersionUID = 2342323423423L;


    private long courseId;
    private long surveyId;
}
