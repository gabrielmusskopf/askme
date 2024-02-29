package br.com.gabrielgmusskopf.myquestion.domain;

public interface AnswerQuestionService {

  void answer(String questionId, QuestionAnswerDTO questionAnswer);

  record QuestionAnswerDTO(String answerId) {

  }

}
