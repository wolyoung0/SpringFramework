package com.mysite.sbb.answer.entity;

import com.mysite.sbb.question.entity.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// 이 클래스가 엔티티 즉, DB에서 테이블 임을 나타냄.
@Entity
// 어노테이션을 통해 Getter, Setter, ToString, 생성자 등을 코드로 작성하는 것 대신 사용
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id // 테이블의 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이건 기억이 안난다. 검색해봐야 될 듯
    @Column(name = "answer_id") // DB 컬럼명
    private Long id; // 아이디

    @Column(columnDefinition = "TEXT") // DB 컬럼 정의(텍스트)
    private String content; // 내용

    // 가끔 LocalDateTime이 인식되지 않는 오류가 발생. jdk 24버전을 사용하면 된다는데 21 버전에서는 왜 안되는지는 검색해봐야됨.
    private LocalDateTime created; // 생성일

    // 1대다 매핑을 위해 사용.
    // 참조하는 클래스가 ManyToOne 참조되는 클래스가 OneToMany
    // fetch는 검색해봐야됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false) // join할 떄 사용. name을 통해 외래키 설정. null 값 비허용 설정
    private Question question;
}
