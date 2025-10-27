package com.mysite.sbb.toolsurvey.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class ToolSurveyDto {

    @NotEmpty(message = "닉네임은 필수 항목 입니다.")
    @Size(min = 2, max = 15, message = "닉네임은 2~15자로 입력하세요.")
    private String nickname;

    @NotEmpty(message = "선호 개발 도구를 선택하세요.")
    private String preferredTool;

    @NotEmpty(message = "팀 프로젝트 방식을 선택하세요.")
    private String teamMode;

    @NotEmpty(message = "경험 수준을 선택하세요.")
    private String experience;

    @Size(max = 150, message = "비고는 최대 150자까지 입력할 수 있습니다.")
    private String note;
}
