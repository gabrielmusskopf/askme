package br.com.gabrielgmusskopf.askme.domain;

public interface AnswerQuestionService {

  void answer(String questionId, QuestionAnswerDTO questionAnswer);

  record QuestionAnswerDTO(String answerId) {

  }

}
