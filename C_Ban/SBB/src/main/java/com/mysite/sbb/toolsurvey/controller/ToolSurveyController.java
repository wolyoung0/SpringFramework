package com.mysite.sbb.toolsurvey.controller;

import com.mysite.sbb.toolsurvey.dto.ToolSurveyDto;
import com.mysite.sbb.toolsurvey.entity.ToolSurvey;
import com.mysite.sbb.toolsurvey.service.ToolSurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/toolSurvey")
@RequiredArgsConstructor
@Slf4j
public class ToolSurveyController {

    private final ToolSurveyService toolSurveyService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<ToolSurvey> paging = toolSurveyService.getList(page);

        log.info("==========paging : {}", paging);
        model.addAttribute("paging", paging);
        return "toolSurvey/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        ToolSurvey toolSurvey = toolSurveyService.getToolSurvey(id);
        log.info("==========> toolSurvey : " + toolSurvey); // 로그 찍는 용도로 사용. error, degug, info 등
        model.addAttribute("toolSurvey", toolSurvey);
        return "toolSurvey/detail";
    }

    @GetMapping("/create")
    public String inputForm(ToolSurveyDto toolSurveyDto, Model model) {
        model.addAttribute("toolSurveyDto", toolSurveyDto);
        return "toolSurvey/inputForm";
    }

    @PostMapping("/create")
    public String createQuestion (@Valid ToolSurveyDto toolSurveyDto, BindingResult bindingResult) {
        log.info("==========> {}", toolSurveyDto);

        if(bindingResult.hasErrors()) {
            return "toolSurvey/inputForm";
        }

        toolSurveyService.createToolSurvey(toolSurveyDto);
        return "redirect:/toolSurvey/list";
    }
}
