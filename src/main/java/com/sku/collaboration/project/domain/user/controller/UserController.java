package com.sku.collaboration.project.domain.user.controller;

import com.sku.collaboration.project.domain.ask.dto.request.AskWordRequest;
import com.sku.collaboration.project.domain.ask.dto.response.AskWordResponse;
import com.sku.collaboration.project.domain.user.dto.request.AskWordIdRequest;
import com.sku.collaboration.project.domain.user.dto.request.SignUpRequest;
import com.sku.collaboration.project.domain.user.dto.response.BadgeResponse;
import com.sku.collaboration.project.domain.user.dto.response.SignUpResponse;
import com.sku.collaboration.project.domain.user.service.UserService;
import com.sku.collaboration.project.domain.word.dto.response.WordResponse;
import com.sku.collaboration.project.global.response.BaseResponse;
import com.sku.collaboration.project.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "User 관리 API")
@Slf4j
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입 API", description = "사용자 회원가입을 위한 API")
  @PostMapping("/sign-up")
  public ResponseEntity<BaseResponse<SignUpResponse>> signUp(
      @RequestBody @Valid SignUpRequest signUpRequest) {
    SignUpResponse signUpResponse = userService.signUp(signUpRequest);
    return ResponseEntity.ok(BaseResponse.success("회원가입이 완료되었습니다.", signUpResponse));
  }

  @Operation(summary = "사용자 뱃지 조회 API", description = "질문/단어장 활동을 기반으로 현재 뱃지 등급을 조회합니다.")
  @GetMapping("/badges")
  public ResponseEntity<BaseResponse<BadgeResponse>> getBadge(@AuthenticationPrincipal CustomUserDetails userDetails) {
    BadgeResponse badge = userService.getBadge(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("뱃지 조회 성공", badge));
  }

  @Operation(summary = "나의 단어장 조회", description = "내가 생성한 단어장 목록을 조회합니다.")
  @GetMapping("/vocab")
  public ResponseEntity<BaseResponse<Map<String, List<WordResponse>>>> getMyVocabulary(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    Map<String, List<WordResponse>> vocab = userService.getUserVocabulary(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("나의 단어장 조회 성공", vocab));
  }

  @Operation(summary = "질문에 대한 단어 추가하기 API", description = "사용자가 질문 후 단어 보기에서 단어를 추가할 때 요청되는 API")
  @PostMapping("/vocab/{askId}")
  public ResponseEntity<BaseResponse<Boolean>> addWord(
          @PathVariable @Valid Long askId,
          @RequestBody @Valid AskWordIdRequest askWordIdRequest,
          @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    Long userId = customUserDetails.getUser().getId();
    Boolean response = userService.addUserWordsResponse(userId, askWordIdRequest);
    return ResponseEntity.ok(BaseResponse.success("질문한 단어에 대한 저장 요청이 완료되었습니다.", response));
  }
}
