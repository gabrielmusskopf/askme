package br.com.gabrielgmusskopf.askme.infra.in;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class IDResponse {

  public String id;

  public IDResponse(UUID id) {
    this.id = id.toString();
  }

}
