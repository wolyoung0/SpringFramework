package com.mysite.sbb.answer.entity;

import com.mysite.sbb.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 이 클래스가 엔티티 즉, DB에서 테이블 임을 나타냄.
@Entity
// 어노테이션을 통해 Getter, Setter, ToString, 생성자 등을 코드로 작성하는 것 대신 사용
@Getter
@Setter
//@ToString(exclude = "")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Answer {
    @Id // 테이블의 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 db가 자동으로 1씩 증가시켜서 만들도록 설정.
    @Column(name = "answer_id") // DB 컬럼명
    private Long id; // 아이디

    @Column(columnDefinition = "TEXT") // DB 컬럼 정의(텍스트)
    private String content; // 내용

    // @EnableJpaAuditing, @EntityListeners 설정하여 사용.
    @CreatedDate
    private LocalDateTime created; // 생성일

    // 1대다 매핑을 위해 사용.
    // 참조하는 클래스가 ManyToOne 참조되는 클래스가 OneToMany
    // fetch는 검색해봐야됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false) // join할 떄 사용. name을 통해 외래키 설정. null 값 비허용 설정
    private Question question;
}
