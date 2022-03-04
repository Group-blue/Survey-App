package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveOptionRequestDto;
import com.bilgeadam.dto.request.SaveQuestionRequestDto;
import com.bilgeadam.dto.request.SaveTemplateRequestDto;
import com.bilgeadam.mapper.SurveyTemplateMapper;
import com.bilgeadam.repository.ISurveyTemlateRepository;
import com.bilgeadam.repository.entity.PossibleAnswers;
import com.bilgeadam.repository.entity.Question;
import com.bilgeadam.repository.entity.SurveyTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SurveyTemplateService {
    private final ISurveyTemlateRepository surveyTemlateRepository;
    private final PossibleAnswersService possibleAnswersService;
    private final QuestionService questionService;
    private final SurveyTemplateMapper mapper;

    public SurveyTemplateService(ISurveyTemlateRepository surveyTemlateRepository, PossibleAnswersService possibleAnswersService,
                                 QuestionService questionService, SurveyTemplateMapper mapper) {
        this.surveyTemlateRepository = surveyTemlateRepository;
        this.possibleAnswersService = possibleAnswersService;
        this.questionService = questionService;
        this.mapper = mapper;
    }

    public SurveyTemplate save(SurveyTemplate surveyTemplate){
        return surveyTemlateRepository.save(surveyTemplate);
    }

    public List<SurveyTemplate> saveList(List<SurveyTemplate> surveyTemplates){
        return surveyTemlateRepository.saveAll(surveyTemplates);
    }

    @Transactional
    public void saveSurveyTemplate(SaveTemplateRequestDto saveTemplateRequestDto){
        List<Question> questions = new ArrayList<>();
        for (SaveQuestionRequestDto saveQuestionRequestDto: saveTemplateRequestDto.getQuestions()){
            Question tempQuestion = mapper.mapQuestionDtoToQuestion(saveQuestionRequestDto);
            if (!saveQuestionRequestDto.getOptions().isEmpty()){
                List<PossibleAnswers> possibleAnswersList = possibleAnswersService.mapOptionRequestDtoToPossibleAnswers(saveQuestionRequestDto.getOptions());
                List<PossibleAnswers> possibleAnswersListFromDb = possibleAnswersService.saveList(possibleAnswersList);
                tempQuestion.setPossibleAnswers(Set.copyOf(possibleAnswersListFromDb));
            }
            questions.add(tempQuestion);
        }

        List<Question> questionsFromDb = questionService.saveList(questions);

        SurveyTemplate surveyTemplate;
        if (!saveTemplateRequestDto.isDraft()){
            surveyTemplate = SurveyTemplate.builder().templateCode(saveTemplateRequestDto.getTemplateName())
                    .version(1L)
                    .explanation(saveTemplateRequestDto.getExplanation())
                    .validityStartDate(new Date().getTime())
                    .isDraft(saveTemplateRequestDto.isDraft())
                    .questions(Set.copyOf(questionsFromDb))
                    .build();
        } else {
            surveyTemplate = SurveyTemplate.builder().templateCode(saveTemplateRequestDto.getTemplateName())
                    .explanation(saveTemplateRequestDto.getExplanation())
                    .isDraft(saveTemplateRequestDto.isDraft())
                    .questions(Set.copyOf(questionsFromDb))
                    .build();
        }
        surveyTemlateRepository.save(surveyTemplate);
    }
}
