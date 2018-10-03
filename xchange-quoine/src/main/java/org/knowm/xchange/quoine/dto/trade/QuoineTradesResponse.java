package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.quoine.service.QuoineTrade;

public class QuoineTradesResponse {
  public final List<QuoineTrade> models;
  public final int currentPage;
  public final int totalPages;

  public QuoineTradesResponse(
      @JsonProperty("models") List<QuoineTrade> models,
      @JsonProperty("current_page") int currentPage,
      @JsonProperty("total_pages") int totalPages) {
    this.models = models;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
  }
}
