package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answer")
@Slf4j
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Long id, @Valid AnswerDto answerDto, BindingResult bindingResult, Model model) { // Valid하면 값이 돌아오기 때문에 BindingResult 설정
//        log.info("==========> {}, {}", id, content);
        Question question = questionService.getQuestion(id); // 외래키 정보를 가져옴.

        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("answerDto", answerDto);
            return "question/detail";
        }

        answerService.create(question, answerDto); // 실제 answer가 생성됨.
        return "redirect:/question/detail/" + id; // 이 주소로 되돌아 옴.
    }
}
