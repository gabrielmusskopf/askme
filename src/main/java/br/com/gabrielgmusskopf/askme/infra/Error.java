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
public class Error {

  private int status;
  @With private String message;
  @With private String reason;
  @With private String detail;
  @With private LocalDateTime timestamp;

  private Error(HttpStatus status) {
    this.status = status.value();
  }

  public static Error badRequest() {
    return new Error(HttpStatus.BAD_REQUEST);
  }

  public static Error notFound() {
    return new Error(HttpStatus.NOT_FOUND);
  }

  public static Error internalError() {
    return new Error(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
