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
public class SurveyTemplate {
    @Id
    @SequenceGenerator(name = "sq_survey_template_id", sequenceName = "sq_survey_template_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sq_survey_template_id")
    private long id;
    private String templateCode;
    private long version;
    private String explanation;
    private long validityStartDate;
    private long validityEndDate;
    private boolean isDraft;

    @OneToMany(mappedBy = "surveyTemplate")
    private Set<Survey> surveys;

    @ManyToMany
    @JoinTable(name = "survey_template_questions", joinColumns = {
            @JoinColumn(name = "survey_template_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "question_id")
    })
    private Set<Question> questions;
}
