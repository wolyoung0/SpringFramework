package com.mysite.sbb.member.entity;

import com.mysite.sbb.member.constant.Department;
import com.mysite.sbb.member.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 사용자 ID

    @Column(nullable = false)
    private String password; // 패스워드

    @Column(unique = true, nullable = false)
    private String email; // 이메일

    @Enumerated(EnumType.STRING) // 스프링한테 enum인지 알려주기 위함. ORDINAL : 숫자, STRING : 문자열 로 DB에 저장됨.
    @Column(nullable = false, length = 10)
    private Gender gender; // 성별

    @Enumerated(EnumType.STRING) // 스프링한테 enum인지 알려주기 위함. ORDINAL : 숫자, STRING : 문자열 로 DB에 저장됨.
    @Column(nullable = false, length = 30)
    private Department department; // 학과

    @Column(nullable = false)
    private Boolean registration; // 등록 여부

    @CreatedDate
    private LocalDateTime created; // 생성일
}
