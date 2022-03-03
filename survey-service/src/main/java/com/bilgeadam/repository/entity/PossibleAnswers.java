package com.bilgeadam.repository.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PossibleAnswers {
    @Id
    @SequenceGenerator(name = "sq_possible_answer_id", sequenceName = "sq_possible_answer_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_possible_answer_id")
    private long id;
    private int orderNo;
    private String description;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
