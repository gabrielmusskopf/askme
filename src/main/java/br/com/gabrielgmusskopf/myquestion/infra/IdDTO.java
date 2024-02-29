package br.com.gabrielgmusskopf.myquestion.infra;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IdDTO {

  public String id;

  public IdDTO(UUID id) {
    this.id = id.toString();
  }

}
