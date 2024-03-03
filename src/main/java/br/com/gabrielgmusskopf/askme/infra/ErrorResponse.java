package br.com.gabrielgmusskopf.askme.infra;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private int status;
  @With private String message;
  @With private String reason;
  @With private String detail;
  @With private LocalDateTime timestamp;

  private ErrorResponse(HttpStatus status) {
    this.status = status.value();
  }

  public static ErrorResponse badRequest() {
    return new ErrorResponse(HttpStatus.BAD_REQUEST);
  }

  public static ErrorResponse notFound() {
    return new ErrorResponse(HttpStatus.NOT_FOUND);
  }

  public static ErrorResponse internalError() {
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
