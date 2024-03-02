package br.com.gabrielgmusskopf.askme.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagedContent<T> {

  private int page;
  private int totalPages;
  private T content;

  public static <R> PagedContent<R> empty(R content) {
    return new PagedContent<>(0, 0, content);
  }

}
