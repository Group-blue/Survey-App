package com.bilgeadam.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class QuestionType {
    @Id
    @SequenceGenerator(name = "sq_question_type_id", sequenceName = "sq_question_type_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_question_type_id")
    private long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "questionType")
    private Set<Question> questions;
}
