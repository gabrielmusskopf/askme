package br.com.gabrielgmusskopf.askme.domain;

public interface GetScoreService {

  ScoreDTO get();

  record ScoreDTO(int answered, int right, int wrong) {
  }

}
