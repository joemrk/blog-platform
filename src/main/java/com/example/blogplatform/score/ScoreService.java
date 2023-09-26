package com.example.blogplatform.score;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScoreService {
  
private final ScoreRepository scoreRepository;

  @Autowired
  public ScoreService(ScoreRepository scoreRepository) {
    this.scoreRepository = scoreRepository;
  }

  public Map<Long, Integer> getPostScore(Set<Long> postId) {
    List<Score> score =  this.scoreRepository.findByPostIdIn(postId);
    return score.stream()
                .collect(Collectors.groupingBy(Score::getPostId,
                        Collectors.summingInt(Score::getScore)));
  }

  public void scoreUp(Long postId, Long userId) {
    Score exist = this.scoreRepository.findByPostIdAndUserId(postId, userId);
    if(exist == null) {
      this.scoreRepository.save(
        Score.builder()
        .postId(postId)
        .userId(userId)
        .score((byte)1)
        .build());
      return;
    }

    if(exist.getScore() < 0) {
      this.scoreRepository.delete(exist);
    }
  }

  public void scoreDown(Long postId, Long userId) {
    Score exist = this.scoreRepository.findByPostIdAndUserId(postId, userId);
    if(exist == null) {
      this.scoreRepository.save(
        Score.builder()
        .postId(postId)
        .userId(userId)
        .score((byte)-1)
        .build());
      return;
    }

    if(exist.getScore() > 0) {
      this.scoreRepository.delete(exist);
    }
  }
}
