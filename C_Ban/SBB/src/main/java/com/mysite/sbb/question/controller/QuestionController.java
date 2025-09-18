package com.mysite.sbb.question.controller;

import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Slf4j // lombok에서 제공하는 로그 도와주는 것
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getList();
        log.info("list : " + questionList);
        model.addAttribute("questionList", questionList); // model.addAttriubte("사용할 이름", 사용할 데이터)
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Question question = questionService.getQuestion(id);
        log.info("==========> question : " + question); // 로그 찍는 용도로 사용. error, degug, info 등
        model.addAttribute("question", question);
        return "question/detail";
    }
}
