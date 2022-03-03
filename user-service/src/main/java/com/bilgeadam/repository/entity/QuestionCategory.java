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
public class QuestionCategory {
    @Id
    @SequenceGenerator(name = "sq_question_category_id", sequenceName = "sq_question_category_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_question_category_id")
    private long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "questionCategory")
    private Set<Question> questions;
}
