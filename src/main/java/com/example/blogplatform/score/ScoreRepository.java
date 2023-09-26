package com.example.blogplatform.score;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
  
  // @Query("select new com.example.blogplatform.score.dto.PostScores(s.post_id, sum(s.score)) " +
  //          "from Score s " +
  //          "where s.post_id IN :postIds " +
  //          "group by s.post_id")
  //   List<PostScores> getSummedScoresByPostIds(@Param("postIds") Set<Long> postIds);

  List<Score> findByPostIdIn(Set<Long> ids);

  Score findByPostIdAndUserId(Long postId, Long userId);

}
