package org.knowm.xchange.quoine.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoineExecutionsResponse {
  public final List<QuoineExecution> models;
  public final int currentPage;
  public final int totalPages;

  public QuoineExecutionsResponse(@JsonProperty("models") List<QuoineExecution> models, @JsonProperty("current_page") int currentPage, @JsonProperty("total_pages") int totalPages) {
    this.models = models;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
  }
}
