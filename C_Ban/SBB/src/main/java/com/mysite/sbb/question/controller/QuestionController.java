package com.mysite.sbb.question.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Slf4j // lombok에서 제공하는 로그 도와주는 것
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, Principal principal) {
        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        questionService.delete(question);

        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         @Valid QuestionDto questionDto,
                         BindingResult bindingResult,
                         Principal principal) {

        if(bindingResult.hasErrors()) {
            return "redirect:/question/inputForm";
        }

        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionService.modify(question, questionDto);

        return "redirect:/question/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         QuestionDto questionDto,
                         Principal principal) {
        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionDto.setSubject(question.getSubject());
        questionDto.setContent(question.getContent());

        return "question/inputForm";
    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword) {
//        int page = 0; // 매개변수로 변경하여 하드코딩 제외
        log.info("========== page : {}, keyword : {}", page, keyword);
        Page<Question> paging = questionService.getList(page, keyword);
//        List<Question> questionList = questionService.getList();

        model.addAttribute("paging", paging); // model.addAttriubte("사용할 이름", 사용할 데이터)
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Question question = questionService.getQuestion(id);
        log.info("==========> question : " + question); // 로그 찍는 용도로 사용. error, degug, info 등
        model.addAttribute("question", question);
        model.addAttribute("answerDto", new AnswerDto());
        return "question/detail";
    }

    @GetMapping("/create")
    public String inputForm(QuestionDto questionDto, Model model) {
        model.addAttribute("questionDto", questionDto); // inputForm.html에서 th:object="${questionDto}"이 뭔지 알려주기 위함.
        return "question/inputForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQuestion (@Valid QuestionDto questionDto, BindingResult bindingResult, Principal principal) {
        log.info("==========> {}", questionDto);

        if(bindingResult.hasErrors()) {
            return "question/inputForm";
        }

        log.info("========== principal : {}", principal.getName());
        Member member = memberService.getMember(principal.getName());

        questionService.createQuestion(questionDto, member);
        return "redirect:/question/list";
    }
}
