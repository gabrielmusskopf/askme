package br.com.gabrielgmusskopf.myquestion.domain;

public interface GetScoreService {

  ScoreDTO get();

  record ScoreDTO(int answered, int right, int wrong) {
  }

}
