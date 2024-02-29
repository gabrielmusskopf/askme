package br.com.gabrielgmusskopf.myquestion.domain.exception;

public class QuestionDuplicatedException extends BusinessException {

  public QuestionDuplicatedException(String message) {
    super(message);
  }

}
