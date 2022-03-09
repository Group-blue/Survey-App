package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveOptionRequestDto;
import com.bilgeadam.dto.response.OptionDetailsResponseDto;
import com.bilgeadam.mapper.SurveyTemplateMapper;
import com.bilgeadam.repository.IPossibleAnswersRepository;
import com.bilgeadam.repository.ISurveyTemlateRepository;
import com.bilgeadam.repository.entity.PossibleAnswers;
import com.bilgeadam.repository.entity.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PossibleAnswersService {
    private final IPossibleAnswersRepository possibleAnswersRepository;
    private final ISurveyTemlateRepository iSurveyTemlateRepository;
    private final SurveyTemplateMapper mapper;
    private final QuestionService questionService;


    public PossibleAnswersService(IPossibleAnswersRepository possibleAnswersRepository, ISurveyTemlateRepository iSurveyTemlateRepository, SurveyTemplateMapper mapper, QuestionService questionService) {
        this.possibleAnswersRepository = possibleAnswersRepository;
        this.iSurveyTemlateRepository = iSurveyTemlateRepository;
        this.mapper = mapper;
        this.questionService = questionService;
    }

    public PossibleAnswers save(PossibleAnswers possibleAnswers){
        return possibleAnswersRepository.save(possibleAnswers);
    }

    public List<PossibleAnswers> saveList(List<PossibleAnswers> possibleAnswersList){
        return possibleAnswersRepository.saveAll(possibleAnswersList);
    }

    public List<PossibleAnswers> mapOptionRequestDtoToPossibleAnswers(List<SaveOptionRequestDto> dtos){
        List<PossibleAnswers> resultList = new ArrayList<>();
        for (SaveOptionRequestDto dto:dtos){
            resultList.add(mapper.mapDtoToPossibleAnswers(dto));
        }
        return resultList;
    }
    public Set<PossibleAnswers> mapOptionDetailResponseDtoToPossibleAnswers(Set<OptionDetailsResponseDto> dtos){
        Set<PossibleAnswers> resultList = new HashSet<>();
        for(OptionDetailsResponseDto dto: dtos){
            resultList.add(mapper.mapOptionDetailResponseDtoToPossibleAnswers(dto));
        }
        return resultList;
    }
}
