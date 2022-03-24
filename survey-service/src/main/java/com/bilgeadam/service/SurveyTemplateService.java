package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveOptionRequestDto;
import com.bilgeadam.dto.request.SaveQuestionRequestDto;
import com.bilgeadam.dto.request.SaveTemplateRequestDto;
import com.bilgeadam.dto.response.ListAllTemplateResponseDto;
import com.bilgeadam.dto.response.OptionDetailsResponseDto;
import com.bilgeadam.dto.response.QuestionDetailResponseDto;
import com.bilgeadam.dto.response.TemplateDetailsResponseDto;
import com.bilgeadam.mapper.SurveyTemplateMapper;
import com.bilgeadam.repository.ISurveyTemlateRepository;
import com.bilgeadam.repository.entity.PossibleAnswers;
import com.bilgeadam.repository.entity.Question;
import com.bilgeadam.repository.entity.SurveyTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
                for(PossibleAnswers possibleAnswer : possibleAnswersList){
                    possibleAnswer.setQuestion(tempQuestion);
                }
                tempQuestion.setPossibleAnswers(Set.copyOf(possibleAnswersList));
            }
            questions.add(tempQuestion);
        }

        List<Question> questionsFromDb = questionService.saveList(questions);
        for(Question question: questionsFromDb){
            if(question.getPossibleAnswers()!=null)
                possibleAnswersService.saveList(new ArrayList<>(question.getPossibleAnswers()));
        }

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

    public List<ListAllTemplateResponseDto> listAllTemplates (){
        List<SurveyTemplate> templates = surveyTemlateRepository.findAll();
        List<ListAllTemplateResponseDto> dtos= new ArrayList<>();
        for (SurveyTemplate surveyTemplate: templates) {
            dtos.add(ListAllTemplateResponseDto.builder()
                    .id(surveyTemplate.getId())
                    .isDraft(surveyTemplate.isDraft())
                    .templateName(surveyTemplate.getTemplateCode())
                    .validityStartDate(surveyTemplate.getValidityStartDate())
                    .build());
        }
        return dtos;
    }

    public TemplateDetailsResponseDto getTemplateDetailsById(long templateId){
        Optional<SurveyTemplate> templateOptional = surveyTemlateRepository.findById(templateId);
        TemplateDetailsResponseDto templateDetailsResponseDto;
        if (templateOptional.isPresent()){
            Set<QuestionDetailResponseDto> questionDetailResponseDtos = new HashSet<>();
            SurveyTemplate template = templateOptional.get();

            for (Question question:template.getQuestions()){
                Set<OptionDetailsResponseDto> optionDetailsResponseDtos = new HashSet<>();
                for (PossibleAnswers answers: question.getPossibleAnswers()){
                    OptionDetailsResponseDto tempAnswer = OptionDetailsResponseDto.builder()
                            .id(answers.getId())
                            .orderNo(answers.getOrderNo())
                            .description(answers.getDescription())
                            .build();
                    optionDetailsResponseDtos.add(tempAnswer);
                }

                QuestionDetailResponseDto tempQuestion = QuestionDetailResponseDto.builder()
                        .id(question.getId())
                        .orderNo(question.getOrderNo())
                        .title(question.getTitle())
                        .text(question.getText())
                        .type(question.getType())
                        .options(optionDetailsResponseDtos)
                        .build();

                questionDetailResponseDtos.add(tempQuestion);
            }

            templateDetailsResponseDto = TemplateDetailsResponseDto.builder()
                    .id(template.getId())
                    .templateName(template.getTemplateCode())
                    .version(template.getVersion())
                    .explanation(template.getExplanation())
                    .validityStartDate(template.getValidityStartDate())
                    .validityEndDate(template.getValidityEndDate())
                    .isDraft(template.isDraft())
                    .questions(questionDetailResponseDtos)
                    .build();

            return templateDetailsResponseDto;
        }
        else {
            return new TemplateDetailsResponseDto();
        }
    }
    @Transactional
    public boolean updateTemplate(TemplateDetailsResponseDto dtoFromUser) {
        List<Question> questions = new ArrayList<>();
        for (QuestionDetailResponseDto questionDto: dtoFromUser.getQuestions()){
           Question tempQuestion = mapper.mapQuestionDetailResponseDtoToQuestion(questionDto);
            if (!questionDto.getOptions().isEmpty()){
                Set<PossibleAnswers> possibleAnswersList = possibleAnswersService.mapOptionDetailResponseDtoToPossibleAnswers(questionDto.getOptions());
                for(PossibleAnswers possibleAnswer : possibleAnswersList){
                    possibleAnswer.setQuestion(tempQuestion);
                }
                tempQuestion.setPossibleAnswers(possibleAnswersList);
            }
            questions.add(tempQuestion);
        }
        List<Question> questionsFromDb = questionService.saveList(questions);
        for(Question question: questionsFromDb){
            possibleAnswersService.saveList(new ArrayList<>(question.getPossibleAnswers()));
        }
        SurveyTemplate surveyTemplate;
        if (!dtoFromUser.isDraft()){
            surveyTemplate = SurveyTemplate.builder().templateCode(dtoFromUser.getTemplateName())
                    .id(dtoFromUser.getId())
                    .version(1L)
                    .explanation(dtoFromUser.getExplanation())
                    .validityStartDate(new Date().getTime())
                    .isDraft(dtoFromUser.isDraft())
                    .questions(Set.copyOf(questionsFromDb))
                    .build();
        } else {
            surveyTemplate = SurveyTemplate.builder().templateCode(dtoFromUser.getTemplateName())
                    .id(dtoFromUser.getId())
                    .explanation(dtoFromUser.getExplanation())
                    .isDraft(dtoFromUser.isDraft())
                    .questions(Set.copyOf(questionsFromDb))
                    .build();
        }
        surveyTemlateRepository.save(surveyTemplate);
        return true;
    }
}
