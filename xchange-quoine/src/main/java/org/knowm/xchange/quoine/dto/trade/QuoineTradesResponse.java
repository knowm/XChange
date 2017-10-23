package org.knowm.xchange.quoine.dto.trade;

import java.util.List;

import org.knowm.xchange.quoine.service.QuoineTrade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoineTradesResponse {
  public final List<QuoineTrade> models;
  public final int currentPage;
  public final int totalPages;

  public QuoineTradesResponse(@JsonProperty("models") List<QuoineTrade> models, @JsonProperty("current_page") int currentPage, @JsonProperty("total_pages") int totalPages) {
    this.models = models;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
  }
}
