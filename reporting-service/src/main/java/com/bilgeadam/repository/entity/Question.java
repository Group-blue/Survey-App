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
public class Question {
    @Id
    @SequenceGenerator(name = "sq_question_id", sequenceName = "sq_question_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_question_id")
    private long id;
    private int orderNo;
    private String title;
    private String text;
    private boolean isOptional;
    private int type;

    @ManyToOne
    @JoinColumn(name = "question_category_id")
    private QuestionCategory questionCategory;


    @ManyToMany(mappedBy = "questions")
    private Set<SurveyTemplate> surveyTemplates;

    @OneToMany(mappedBy = "question")
    private Set<PossibleAnswers> possibleAnswers;

    @OneToMany(mappedBy = "question")
    private Set<StudentAnswers> answers;
}
