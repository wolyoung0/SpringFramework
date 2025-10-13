package com.mysite.sbb.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data // Getter, Setter, toString 등이 한번에 들어있음. AllArgsConstructor는 없음. NoArgsConstructor는 있음.
@Builder
public class QuestionDto {

    @NotEmpty(message = "제목은 필수 항목 입니다.") // 공백 문자열 제외
    @Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.") // 크기 미리 지정. build.gradle에서 의존성 추가
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
