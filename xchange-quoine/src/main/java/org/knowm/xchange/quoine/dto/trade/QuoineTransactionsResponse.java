package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class QuoineTransactionsResponse {
  public final List<QuoineTransaction> models;
  public final int currentPage;
  public final int totalPages;

  public QuoineTransactionsResponse(
      @JsonProperty("models") List<QuoineTransaction> models,
      @JsonProperty("current_page") int currentPage,
      @JsonProperty("total_pages") int totalPages) {
    this.models = models;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
  }
}
