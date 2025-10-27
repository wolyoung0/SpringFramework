package com.mysite.sbb.toolsurvey.service;

import com.mysite.sbb.toolsurvey.dto.ToolSurveyDto;
import com.mysite.sbb.toolsurvey.entity.ToolSurvey;
import com.mysite.sbb.toolsurvey.repository.ToolSurveyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolSurveyService {

    private final ToolSurveyRepository toolSurveyRepository;

    public Page<ToolSurvey> getList(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<ToolSurvey> paging = toolSurveyRepository.findAll(pageable);

        return paging;
    }

    public ToolSurvey getToolSurvey(Long id) {
        return toolSurveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 질문이 X"));
    }

    public void createToolSurvey(ToolSurveyDto toolSurveyDto) {
        ToolSurvey toolSurvey = ToolSurvey.builder()
                .note(toolSurveyDto.getNote())
                .experience(toolSurveyDto.getExperience())
                .teamMode(toolSurveyDto.getTeamMode())
                .preferredTool(toolSurveyDto.getPreferredTool())
                .nickname(toolSurveyDto.getNickname())
                .build();
        toolSurveyRepository.save(toolSurvey);
    }
}
