package com.mysite.sbb.question.entity;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.audit.BaseEntity;
import com.mysite.sbb.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

// 이 클래스가 엔티티 즉, DB에서 테이블 임을 나타냄.
@Entity
//@Table(name = "my_question") // 클래스명과 다르게 DB 테이블 명을 설정하고 싶을 때 사용
// 어노테이션을 통해 Getter, Setter, ToString, 생성자 등을 코드로 작성하는 것 대신 사용
@Getter
@Setter
// 상호 참조로 인해 에러 발생해서 삭제
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class) // 변경에 대한 부분 자동 패치
public class Question extends BaseEntity {

    @Id // 테이블의 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이건 기억이 안난다. 검색해봐야 될 듯
    @Column(name = "question_id") // DB 컬럼명
    private Long id; // 아이디

    @Column(length = 200, nullable = false) // DB 컬럼 설정(문자열 길이 200, null 불가)
    private String subject; // 주제

    @Column(columnDefinition = "TEXT") // DB 컬럼 정의(텍스트)
    private String content; // 내용

    // @EnableJpaAuditing, @EntityListeners 설정하여 사용.
    // 가끔 21버전에서만 인식이 안된다고 하셨는데 인터넷에선 설정 문제라고 함.
//    @CreatedDate // 언제 만들었는지 자동 생성, SbbApplication에서 @EnableJpaAuditing 설정
//    private LocalDateTime created; // 생성일

    // 참조되는 테이블에서 참조하는 테이블에 대한 설정. mappedBy는 question 테이블 참조
    // fetch: 참조하는 데이터를 언제 읽어올지 설정. LAZY: 엔티티를 사용하는 시점에 쿼리 실행(권장)
    // 상태 변화 시 cascade를 위한 설정. cascade 타입은 여러개 존재. ALL: 모든 상태 변화 실행.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;
}
