package com.sku.collaboration.project.domain.user.controller;

import com.sku.collaboration.project.domain.user.dto.request.LanguageUpdateRequest;
import com.sku.collaboration.project.domain.user.dto.request.NameUpdateRequest;
import com.sku.collaboration.project.domain.user.dto.request.PasswordUpdateRequest;
import com.sku.collaboration.project.domain.user.dto.request.SignUpRequest;
import com.sku.collaboration.project.domain.user.dto.response.SignUpResponse;
import com.sku.collaboration.project.domain.user.service.UserService;
import com.sku.collaboration.project.global.response.BaseResponse;
import com.sku.collaboration.project.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


  @Operation(summary = "비밀번호 변경 API", description = "사용자 비밀번호 변경을 위한 API")
  @PatchMapping("/password")
  public ResponseEntity<BaseResponse<Void>> changePassword(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
    userService.changePassword(userDetails.getUser().getUserId(), passwordUpdateRequest);
    return ResponseEntity.ok(BaseResponse.success("비밀번호가 변경되었습니다.", null));
  }

  // 언어 변경
  @Operation(summary = "언어 변경 API", description = "사용자 언어 변경을 위한 API")
  @PatchMapping("/language")
  public ResponseEntity<BaseResponse<Void>> changeLanguage(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody LanguageUpdateRequest newLanguage) {
    userService.changeLanguage(userDetails.getUser().getUserId(), newLanguage);
    return ResponseEntity.ok(BaseResponse.success("언어가 변경되었습니다.", null));
  }

  // 사용자 이름 변경
  @Operation(summary = "사용자 이름 변경 API", description = "사용자 이름 변경을 위한 API")
  @PatchMapping("/name")
  public ResponseEntity<BaseResponse<Void>> changeName(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid NameUpdateRequest nameUpdateRequest) {
    userService.changeName(userDetails.getUser().getUserId(), nameUpdateRequest);
    return ResponseEntity.ok(BaseResponse.success("사용자 이름이 변경되었습니다.", null));
  }

}
