package com.sku.collaboration.project.domain.word.repository;

import com.sku.collaboration.project.domain.word.entity.Word;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {

  /**
   * 특정 사용자가 작성한 단어 개수를 조회
   *
   * @param userId 사용자 ID
   * @return 해당 사용자의 단어 수
   */
  int countByUserId(Long userId);

  /**
   * 특정 사용자가 작성한 모든 단어를 생성일 내림차순으로 조회
   *
   * @param userId 사용자 ID
   * @return 단어 리스트 (최신순 정렬)
   */
  List<Word> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}


