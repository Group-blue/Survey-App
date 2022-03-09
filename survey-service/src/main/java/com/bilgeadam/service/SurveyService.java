package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveSurveyRequestDto;
import com.bilgeadam.dto.response.CourseBasicResponseDto;
import com.bilgeadam.dto.response.ListAllSurveyResponseDto;
import com.bilgeadam.dto.response.SurveyDetailResponseDto;
import com.bilgeadam.mapper.CourseMapper;
import com.bilgeadam.repository.ISurveyRepository;
import com.bilgeadam.repository.ISurveyTemlateRepository;
import com.bilgeadam.repository.entity.Survey;
import com.bilgeadam.repository.entity.SurveyTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    private final ISurveyRepository surveyRepository;
    private final ISurveyTemlateRepository surveyTemlateRepository;
    private final CourseMapper courseMapper;

    public SurveyService(ISurveyRepository surveyRepository, ISurveyTemlateRepository surveyTemlateRepository, CourseMapper courseMapper) {
        this.surveyRepository = surveyRepository;
        this.surveyTemlateRepository = surveyTemlateRepository;
        this.courseMapper = courseMapper;
    }

    public void save(Survey survey){
        surveyRepository.save(survey);
    }

    public List<ListAllSurveyResponseDto> listAllSurveys(){
        List<Survey> surveys = surveyRepository.findAll();
        List<ListAllSurveyResponseDto> dtos = new ArrayList<>();
        for(Survey survey: surveys){
            dtos.add(ListAllSurveyResponseDto.builder()
                            .id(survey.getId())
                            .templateId(survey.getSurveyTemplate().getId())
                            .templateExplanation(survey.getSurveyTemplate().getExplanation())
                            .templateName(survey.getSurveyTemplate().getTemplateCode())
                            .endDate(survey.getEndDate())
                            .startDate(survey.getStartDate())
                    .build());
        }
        return dtos;
    }


    public boolean saveSurvey(SaveSurveyRequestDto saveSurveyRequestDto){
        Optional<SurveyTemplate> surveyTemplate = surveyTemlateRepository.findById(saveSurveyRequestDto.getTemplateId());
        if(surveyTemplate.isPresent()) {
            SurveyTemplate tempSurveyTemplate = surveyTemplate.get();
            Survey survey;
            survey = Survey.builder()
                    .sequenceNumber(saveSurveyRequestDto.getSequenceNumber())
                    .startDate(saveSurveyRequestDto.getStartDate())
                    .endDate(saveSurveyRequestDto.getEndDate())
                    .surveyTemplate(tempSurveyTemplate)
                    .build();
            save(survey);
            return true;
        }else{
            return false;
        }
    }
    public SurveyDetailResponseDto findSurveyById(long surveyId){

        Optional<Survey> surveyOptional = surveyRepository.findById(surveyId);
        SurveyDetailResponseDto surveyDetailResponseDto;
        if (surveyOptional.isPresent()){
            Survey survey = surveyOptional.get();

            CourseBasicResponseDto courseBasicResponseDto = courseMapper.mapCoursetoDto(survey.getCourse());

            surveyDetailResponseDto = SurveyDetailResponseDto.builder()
                    .templateId(survey.getSurveyTemplate().getId())
                    .sequenceNumber(survey.getSequenceNumber())
                    .startDate(survey.getStartDate())
                    .endDate(survey.getEndDate())
                    .dto(courseBasicResponseDto)
                    .build();
            return surveyDetailResponseDto;
        }else {
            return new SurveyDetailResponseDto();
        }
    }
}
