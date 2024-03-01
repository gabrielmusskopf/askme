package br.com.gabrielgmusskopf.myquestion.infra;

import br.com.gabrielgmusskopf.myquestion.domain.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> toResponseEntity(Error error) {
    return ResponseEntity
        .status(error.getStatus())
        .body(error);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleGlobal(Exception ex, HttpServletRequest request) {
    log.error(ex.getMessage(), ex);
    var error = Error.internalError()
        .withMessage("Something worng happended. Try again later.")
        .withReason(ex.getMessage())
        .withDetail(request.getRequestURI())
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }

  @ExceptionHandler({BusinessException.class})
  protected ResponseEntity<Object> handleBusiness(BusinessException ex, HttpServletRequest request) {
    log.error(ex.getMessage(), ex.getCause());
    var error = Error.badRequest()
        .withMessage(ex.getMessage())
        .withDetail(request.getRequestURI())
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }

}
