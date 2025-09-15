package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 통합 테스트(전체 돌릴 수 있음)
class QuestionRepositoryTest {

    @Autowired // 자동으로 외부에서 주입, 자동 객체 생성, 초기화하는 방법 : 생성자, Setter -> 이걸 자동으로 해줌
    private QuestionRepository questionRepository;

    @Test // unit 테스트 생성(따로따로 돌릴 수 있음)
    public void testSave() {
        Question q1 = new Question();
        q1.setSubject("sbb 질문하기");
        q1.setContent("sbb 내용 넣기");
        System.out.println("q1 : " + q1);
        Question q2 = questionRepository.save(q1); // 데베에 저장(save로 q1 객체 저장), insert문을 적는 대신 사용가능
        System.out.println("q2 : " + q2);

        Question q3 = Question.builder()
                .content("질문 내용 입력하기")
                .subject("질문 제목 입력하기")
                .build(); // 기본 생성자 만드는 것과 동일
        Question q4 = questionRepository.save(q3);
        // 유닛 테스트는 하나하나 찍으면 힘들기 때문에 한번에 테스트, 예상 값과 실제 값 비교
        // 예상 값과 틀리면 왜 틀렸는지 확인해서 수정
        assertEquals(2, q4.getId());
    }

    @Test
    public void testFindAll() {
        List<Question> questionList = questionRepository.findAll();
        assertEquals(2, questionList.size());
    }

    @Test
    public void testLike() {

        // Subject안에 sbb라는 단어가 있는지 검색, sbb1이면 엔티티 자체가 안 찾아와 짐 왜지..?
        Question q3 = questionRepository.findBySubjectLike("%sbb%")
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티 존재 X"));
        assertEquals("sbb 질문하기", q3.getSubject());

        // 아래와 같은 내용이지만 람다식으로 짧게 가능
        Question q2 = questionRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티 존재 X"));
             // .orElseThrow(EntityNotFoundException::new); 이렇게도 사용가능
        assertEquals("sbb 질문하기", q2.getSubject());

//        // 1L일 경우 통과, 2L일 경우 값 달라서 에러, 3L일 경우 엔티티 존재하지 않으므로 예외 발생
//        Optional<Question> q1 = questionRepository.findById(2L); // 존재할 수도 있고 존재하지 않을 수도 있으므로 Optional
//        if(q1.isPresent()) { // 존재하는지 확인
//            Question question = q1.get(); // 옵션 + 커맨드 + V, 알트 + 쉬프트 + L로 반환되는 변수 자동 생성
//            assertEquals("sbb 질문하기", question.getSubject());
//        } else { // 존재하지 않을 경우
//            throw new EntityNotFoundException("해당 엔티티 존재 X"); // 엔티티가 존재하지 않을경우 예외처리 발생
//        }
    }
}