package org.knowm.xchange.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithCursor<T> {

  private final T result;
  private final String nextPage;

  public WithCursor(@JsonProperty("result") T result, @JsonProperty("next_page") String nextPage) {
    this.result = result;
    this.nextPage = nextPage;
  }

  public T getResult() {
    return result;
  }

  public String getNextPage() {
    return nextPage;
  }
}
