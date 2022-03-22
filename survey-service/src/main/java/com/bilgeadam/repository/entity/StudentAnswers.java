package com.bilgeadam.repository.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class StudentAnswers {
    @Id
    @SequenceGenerator(name = "sq_student_answers_id", sequenceName = "sq_student_answers_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_student_answers_id")
    private long id;
    private long timestamp;
    private boolean isFinished;
    private String answer;
    private int type;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
