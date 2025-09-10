package com.mysite.sbb.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller와 RestConroller의 차이에 대해서는 알아봐야됨.
// RestController는 이 클래스가 Controller임을 나타내는데 RestAPI형태의 Controller임을 나타내는 걸로 알고 잇음
@RestController
public class IndexController {

    // Get 방식으로 통신(주소가 /(루트))
    @GetMapping(value = "/")
    public String index() {
        return "index"; // resources/templates/index.html을 반환.
    }

    // Get 방식으로 통신(주소가 /temp/abc)
    @GetMapping(value = "/temp/abc")
    public String abc() {
        return "temp/abc"; // resources/templates/temp/abc.html을 반환.
    }
}
