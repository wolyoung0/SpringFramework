package com.mysite.sbb.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity // 테이블 설정
@Getter // 가져오기
@Setter // 세팅
@ToString // 내용 보기
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 모든 생성자
@Builder
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    private int age;

    // Child 엔티티에 적힌 parent라는 변수와 매핑, cascade 할 때 전부 삭제 및 업데이트
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> childList; // 자바에만 존재. 자식은 언제 생길지 모르기 때문에 리스트로 Child 불러옴
}
