package br.com.gabrielgmusskopf.myquestion.domain.exception;

public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }

}
