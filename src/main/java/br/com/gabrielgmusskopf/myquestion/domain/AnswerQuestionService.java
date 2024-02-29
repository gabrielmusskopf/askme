package br.com.gabrielgmusskopf.myquestion.domain;

public interface AnswerQuestionService {

  void answer(QuestionAnswerDTO questionAnswer);

  record QuestionAnswerDTO(String questionId, String answerId) {

  }

}
