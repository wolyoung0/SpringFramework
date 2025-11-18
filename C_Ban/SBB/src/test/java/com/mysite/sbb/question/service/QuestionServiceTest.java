package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dto.QuestionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired // 테스트 코드에서는 final 사용 불가
    private QuestionService questionService;

    @Transactional // all or nothing 이라서 실제 반영은 안되고 테스트 할 때만 사용
    @Test
    void createQuestion() {
        for (int i = 0; i < 300; i++) {
            QuestionDto questionDto = QuestionDto.builder()
                    .subject("질문 제목 " + i)
                    .content("질문 내용 " + i)
                    .build();
//            questionService.createQuestion(questionDto);
        }
        System.out.println("==========리스트 : " + questionService.getList().size());
    }
}