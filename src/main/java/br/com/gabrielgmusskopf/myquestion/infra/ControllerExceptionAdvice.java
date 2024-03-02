package br.com.gabrielgmusskopf.myquestion.infra;

import br.com.gabrielgmusskopf.myquestion.domain.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvice {

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
        .withDetail(completeURI(request))
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }

  @ExceptionHandler({BusinessException.class})
  protected ResponseEntity<Object> handleBusiness(BusinessException ex, HttpServletRequest request) {
    var error = Error.badRequest()
        .withMessage(ex.getMessage())
        .withDetail(completeURI(request))
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpServletRequest request) {
    final var erroMessage = ex.getFieldErrors().stream()
        .map(e -> "'" + e.getField() + "' " + e.getDefaultMessage() + ".")
        .collect(Collectors.joining("."));

    var error = Error.badRequest()
        .withMessage(erroMessage)
        .withDetail(completeURI(request))
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                        HttpServletRequest request) {
    var error = Error.badRequest()
        .withMessage("'" + ex.getParameterName() + "' query param is missing.")
        .withDetail(completeURI(request))
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleArgumentMismatch(MethodArgumentTypeMismatchException ex,
                                                          HttpServletRequest request) {
    var messageBuilder = new StringBuilder("Invalid argument for '" + ex.getName() + "' argument.");

    var requiredType = ex.getRequiredType();
    if (requiredType != null && requiredType.isEnum()) {
      messageBuilder.append(" Valid values are ").append(Arrays.toString(requiredType.getEnumConstants()));
    }

    var error = Error.badRequest()
        .withMessage(messageBuilder.toString())
        .withReason(ex.getMostSpecificCause().getMessage())
        .withDetail(completeURI(request))
        .withTimestamp(LocalDateTime.now());

    return toResponseEntity(error);
  }


  private String completeURI(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder(request.getRequestURI());
    if (StringUtils.isNotBlank(request.getQueryString())) {
      sb.append("?").append(request.getQueryString());
    }
    return sb.toString();
  }

}
