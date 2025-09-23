package com.mysite.sbb.question.dto;

import lombok.Builder;
import lombok.Data;

@Data // Getter, Setter, toString 등이 한번에 들어있음. AllArgsConstructor는 없음. NoArgsConstructor는 있음.
@Builder
public class QuestionDto {

    private String subject;

    private String content;
}
