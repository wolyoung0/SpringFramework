package com.mysite.sbb.toolsurvey.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ToolSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toolsurvey_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String preferredTool;

    @Column(nullable = false)
    private String teamMode;

    @Column(nullable = false)
    private String experience;

    @Column(length = 150)
    private String note;

    @CreatedDate
    private LocalDateTime created;
}
