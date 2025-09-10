package com.mysite.sbb.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 테이블 설정
@Getter // 가져오기
@Setter // 세팅
@ToString // 내용 보기
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 모든 생성자
@Builder
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    private int age;

    private int grade;

    @ManyToOne(fetch = FetchType.LAZY) // 항상 Lazy로 설정
    @JoinColumn(name = "parent_id", nullable = false) // join할 컬럼명, 존재하지 않는걸 조인할 수 없으므로 nullable = false
    private Parent parent;
}
