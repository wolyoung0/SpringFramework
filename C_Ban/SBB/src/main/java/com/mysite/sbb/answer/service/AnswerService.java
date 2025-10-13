package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.question.entity.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, AnswerDto answerDto) {
        Answer answer = Answer.builder()
                .content(answerDto.getContent())
                .question(question)
                .build();
        Answer save = answerRepository.save(answer);
        log.info("==========> " + save);
    }
}
