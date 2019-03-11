package org.knowm.xchange.paymium.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PaymiumMarketDepth {

  private final List<PaymiumMarketOrder> bids;
  private final List<PaymiumMarketOrder> asks;

  /**
   * @param bids
   * @param asks
   */
  public PaymiumMarketDepth(
      @JsonProperty("bids") List<PaymiumMarketOrder> bids,
      @JsonProperty("asks") List<PaymiumMarketOrder> asks) {

    this.bids = bids;
    this.asks = asks;
  }

  public List<PaymiumMarketOrder> getBids() {

    return bids;
  }

  public List<PaymiumMarketOrder> getAsks() {

    return asks;
  }

  @Override
  public String toString() {

    return "PaymiumMarketDepth{" + "bids=" + bids + ", asks=" + asks + '}';
  }
}
