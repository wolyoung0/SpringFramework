package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Slf4j // lombok에서 제공하는 로그 도와주는 것
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
//        int page = 0; // 매개변수로 변경하여 하드코딩 제외
        Page<Question> paging = questionService.getList(page);
//        List<Question> questionList = questionService.getList();

        log.info("==========paging : {}", paging);
        model.addAttribute("paging", paging); // model.addAttriubte("사용할 이름", 사용할 데이터)
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Question question = questionService.getQuestion(id);
        log.info("==========> question : " + question); // 로그 찍는 용도로 사용. error, degug, info 등
        model.addAttribute("question", question);
        return "question/detail";
    }

    @GetMapping("/create")
    public String inputForm(QuestionDto questionDto, Model model) {
        model.addAttribute("questionDto", questionDto);
        model.addAttribute("answerDto", new AnswerDto());
        return "question/inputForm";
    }

    @PostMapping("/create")
    public String createQuestion (@Valid QuestionDto questionDto, BindingResult bindingResult) {
        log.info("==========> {}", questionDto);

        if(bindingResult.hasErrors()) {
            return "question/inputForm";
        }

        questionService.createQuestion(questionDto);
        return "redirect:/question/list";
    }
}
