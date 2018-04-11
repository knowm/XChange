package org.knowm.xchange.ccex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class CCEXBuySellResult {

  private List<CCEXBuySellData> buy = new ArrayList<>();
  private List<CCEXBuySellData> sell = new ArrayList<>();

  /**
   * @param sell
   * @param buy
   */
  public CCEXBuySellResult(
      @JsonProperty("buy") List<CCEXBuySellData> buy,
      @JsonProperty("sell") List<CCEXBuySellData> sell) {
    this.buy = buy;
    this.sell = sell;
  }

  /** @return The buy */
  public List<CCEXBuySellData> getBuy() {
    return buy;
  }

  /** @return The sell */
  public List<CCEXBuySellData> getSell() {
    return sell;
  }
}
