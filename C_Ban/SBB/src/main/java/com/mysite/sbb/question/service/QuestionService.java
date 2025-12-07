package com.mysite.sbb.question.service;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    // 1. 생성자 만들어서 객체 주업
    // 2. Setter 이용해서 생성
    // 3. @Autowired 사용
    // 4. RequiredArgsConstructor 사용 (최신 방법)
    private final QuestionRepository questionRepository; // final을 붙임으로서 값 입력없이 자동 객체 생성

    public Page<Question> getList(int page, String keyword) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

//        Specification<Question> specification = search(keyword);
//        Page<Question> paging = questionRepository.findAll(specification, pageable);

        Page<Question> paging = questionRepository.findAllByKeyword(keyword, pageable);
        return paging;
    }

    private Specification<Question> search(String keyword) {
        return new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> question, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);
                Join<Question, Member> member1 = question.join("author", JoinType.LEFT);
                Join<Question, Answer> answer = question.join("answerList", JoinType.LEFT);
                Join<Answer, Member> member2 = answer.join("author", JoinType.LEFT);

                return criteriaBuilder.or(
                        criteriaBuilder.like(question.get("subject"), "%" + keyword + "%"),
                        criteriaBuilder.like(question.get("content"), "%" + keyword + "%"),
                        criteriaBuilder.like(member1.get("username"), "%" + keyword + "%"),
                        criteriaBuilder.like(member2.get("username"), "%" + keyword + "%"),
                        criteriaBuilder.like(answer.get("content"), "%" + keyword + "%")
                );
            }
        };
    }

    public List<Question> getList() {

//        Pageable pageable = PageRequest.of(page, 10);
        return questionRepository.findAll();
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 질문이 X"));
    }

    public void createQuestion(QuestionDto questionDto, Member member) {
        Question question = Question.builder()
                .content(questionDto.getContent())
                .subject(questionDto.getSubject())
                .author(member)
                .build();
        questionRepository.save(question); // insert 구문
    }

    public void modify(Question question, @Valid QuestionDto questionDto) {
        question.setSubject(questionDto.getSubject());
        question.setContent(questionDto.getContent());
        questionRepository.save(question); // update 구문, JPA가 이미 존재하는 id는 업데이트로 판단.
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
