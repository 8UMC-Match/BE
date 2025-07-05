package com.sku.collaboration.project.domain.userBadge.controller;


import com.sku.collaboration.project.domain.userBadge.dto.BadgeDetailResponse;
import com.sku.collaboration.project.domain.userBadge.dto.MyBadgeListResponse;
import com.sku.collaboration.project.domain.userBadge.service.UserBadgeService;
import com.sku.collaboration.project.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/sp/users/me")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
@Tag(name = "UserBadge", description = "획득한 배찌 관련 API")
public class UserBadgeController {

  private final UserBadgeService userBadgeService;

  @Operation(summary = "획득한 배지 최신순 조회", description = "마이페이지에서 획득한 배찌 리스트 버튼 눌렀을 때 요청되는 API")
  @GetMapping("/badges")
  public ResponseEntity<BaseResponse<List<MyBadgeListResponse>>> getMyBadges() {
    Long userId = 1L; // ⭐(나중에 로그인 구현되면 SecurityContext에서 가져오기)
    List<MyBadgeListResponse> responses = userBadgeService.getMyBadges(userId);
    return ResponseEntity.ok(BaseResponse.success(responses));
  }

  @GetMapping("/badges/{badgeId}")
  public ResponseEntity<BaseResponse<BadgeDetailResponse>> getMyBadgeDetail(
      @PathVariable Long badgeId) {
    BadgeDetailResponse response = userBadgeService.getBadgeDetail(badgeId);
    return ResponseEntity.ok(BaseResponse.success(response));
  }

}
