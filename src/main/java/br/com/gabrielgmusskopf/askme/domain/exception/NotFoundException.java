package br.com.gabrielgmusskopf.askme.domain.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

}
