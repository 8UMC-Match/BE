package com.sku.collaboration.project.domain.ask.controller;

import com.sku.collaboration.project.domain.ask.service.AskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asks")
@Tag(name = "Ask", description = "Ask 관리 API")
public class AskController {

    private final AskService askService;

    //물어보기 /api/asks/..

    //질문에 대한 단어장 보기

    //단어 추가하기(질문에 대한 단어 보기)

    //단어장에 추가하기(나의 단어장에 추가)

    //단어 삭제하기(나의 단어장에서)

    //퀴즈 시작하기

    //퀴즈 답변 제출 + 다음 문제 조회

    //퀴즈 결과 조회

}
