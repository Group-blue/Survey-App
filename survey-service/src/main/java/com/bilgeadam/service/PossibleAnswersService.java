package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveOptionRequestDto;
import com.bilgeadam.mapper.SurveyTemplateMapper;
import com.bilgeadam.repository.IPossibleAnswersRepository;
import com.bilgeadam.repository.entity.PossibleAnswers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PossibleAnswersService {
    private final IPossibleAnswersRepository possibleAnswersRepository;
    private final SurveyTemplateMapper mapper;

    public PossibleAnswersService(IPossibleAnswersRepository possibleAnswersRepository, SurveyTemplateMapper mapper) {
        this.possibleAnswersRepository = possibleAnswersRepository;
        this.mapper = mapper;
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
}
