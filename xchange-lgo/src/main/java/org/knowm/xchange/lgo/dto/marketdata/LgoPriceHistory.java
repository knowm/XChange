package org.knowm.xchange.lgo.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LgoPriceHistory {

  private final List<LgoCandlestick> prices;

  public LgoPriceHistory(@JsonProperty("prices") List<LgoCandlestick> prices) {
    this.prices = prices;
  }

  public List<LgoCandlestick> getPrices() {
    return prices;
  }

  @Override
  public String toString() {
    return "LgoPriceHistory{" + "prices=" + prices + '}';
  }
}
