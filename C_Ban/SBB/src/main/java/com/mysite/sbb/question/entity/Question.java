package com.mysite.sbb.question.entity;

import com.mysite.sbb.answer.entity.Answer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 이 클래스가 엔티티 즉, DB에서 테이블 임을 나타냄.
@Entity
//@Table(name = "my_question") // 클래스명과 다르게 DB 테이블 명을 설정하고 싶을 때 사용
// 어노테이션을 통해 Getter, Setter, ToString, 생성자 등을 코드로 작성하는 것 대신 사용
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id // 테이블의 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이건 기억이 안난다. 검색해봐야 될 듯
    @Column(name = "question_id") // DB 컬럼명
    private Long id; // 아이디

    @Column(length = 200, nullable = false) // DB 컬럼 설정(문자열 길이 200, null 불가)
    private String subjectName; // 주제

    @Column(columnDefinition = "TEXT") // DB 컬럼 정의(텍스트)
    private String content; // 내용

    // 가끔 LocalDateTime이 인식되지 않는 오류가 발생. jdk 24버전을 사용하면 된다는데 21 버전에서는 왜 안되는지는 검색해봐야됨.
    private LocalDateTime created; // 생성일

    // 참조되는 테이블에서 참조하는 테이블에 대한 설정 fetch는 검색해 봐야되고 mappedBy는 question 테이블 참조, 삭제 시 cascade 시킴.
    // cascade 타입은 여러개가 있다. 다른건 검색해봐야됨. ALL은 모든 것을 지원버림.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList;
}
