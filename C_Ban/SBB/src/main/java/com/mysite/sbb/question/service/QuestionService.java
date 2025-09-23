package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    // 1. 생성자 만들어서 객체 주업
    // 2. Setter 이용해서 생성
    // 3. @Autowired 사용
    // 4. RequiredArgsConstructor 사용 (최신 방법)
    private final QuestionRepository questionRepository; // final을 붙임으로서 값 입력없이 자동 객체 생성

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 질문이 X"));
    }

    public void createQuestion(QuestionDto questionDto) {
        Question question = Question.builder()
                .content(questionDto.getContent())
                .subject(questionDto.getSubject())
                .build();
        questionRepository.save(question);
    }
}
