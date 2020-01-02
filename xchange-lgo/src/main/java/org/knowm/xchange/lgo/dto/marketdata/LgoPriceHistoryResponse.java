package org.knowm.xchange.lgo.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoPriceHistoryResponse {

  private final List<List<Object>> prices;

  public LgoPriceHistoryResponse(@JsonProperty("prices") List<List<Object>> prices) {
    this.prices = prices;
  }

  public List<List<Object>> getPrices() {
    return prices;
  }
}
