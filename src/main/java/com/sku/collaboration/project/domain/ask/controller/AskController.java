package com.sku.collaboration.project.domain.ask.controller;

import com.sku.collaboration.project.domain.ask.dto.request.AskRequest;
import com.sku.collaboration.project.domain.ask.dto.request.AskWordRequest;
import com.sku.collaboration.project.domain.ask.dto.response.AskResponse;
import com.sku.collaboration.project.domain.ask.dto.response.AskWordResponse;
import com.sku.collaboration.project.domain.ask.dto.response.AskWordsResponse;
import com.sku.collaboration.project.domain.ask.service.AskService;
import com.sku.collaboration.project.domain.user.dto.request.SignUpRequest;
import com.sku.collaboration.project.domain.user.dto.response.SignUpResponse;
import com.sku.collaboration.project.domain.user.service.UserService;
import com.sku.collaboration.project.global.response.BaseResponse;
import com.sku.collaboration.project.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asks")
@Tag(name = "Ask", description = "Ask 관리 API")
public class AskController {

    private final AskService askService;

    //물어보기 /api/asks/..
    @Operation(summary = "물어보기 API", description = "사용자가 묻고 싶은 문장 질문할 때 요청되는 API")
    @PostMapping
    public ResponseEntity<BaseResponse<AskResponse>> askQuestion(
            @RequestBody @Valid AskRequest askRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        AskResponse response = askService.toAskResponse(userId, askRequest);
        return ResponseEntity.ok(BaseResponse.success("질문 요청이 성공되었습니다.", response));
    }
    //질문에 대한 단어장 보기
    @Operation(summary = "질문에 대한 단어장 보기 API", description = "사용자가 질문 후 단어 보기를 누르면 요청되는 API")
    @GetMapping("/{askId}/vocab")
    public ResponseEntity<BaseResponse<AskWordsResponse>> getAskWords(
            @PathVariable @Valid Long askId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        AskWordsResponse response = askService.toAskWordsResponse(userId, askId);
        return ResponseEntity.ok(BaseResponse.success("질문에 대한 단어장 보기 완료되었습니다.", response));
    }
    //단어 추가하기(질문에 대한 단어 보기)
    @Operation(summary = "질문에 대한 단어 추가하기 API", description = "사용자가 질문 후 단어 보기에서 단어를 추가할 때 요청되는 API")
    @PostMapping("/{askId}/vocab")
    public ResponseEntity<BaseResponse<AskWordResponse>> addWord(
            @PathVariable @Valid Long askId,
            @RequestBody @Valid AskWordRequest askWordRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getUser().getId();
        AskWordResponse response = askService.toAskWordResponse(userId, askId, askWordRequest);
        return ResponseEntity.ok(BaseResponse.success("질문한 단어에 대한 요청이 완료되었습니다.", response));
    }
    //단어장에 추가하기(나의 단어장에 추가)

    //단어 삭제하기(나의 단어장에서)

    //퀴즈 시작하기

    //퀴즈 답변 제출 + 다음 문제 조회

    //퀴즈 결과 조회

}
