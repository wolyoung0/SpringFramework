package com.mysite.sbb.toolsurvey.repository;

import com.mysite.sbb.toolsurvey.entity.ToolSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolSurveyRepository extends JpaRepository<ToolSurvey, Long> {

    Page<ToolSurvey> findAll(Pageable pageable);
}
