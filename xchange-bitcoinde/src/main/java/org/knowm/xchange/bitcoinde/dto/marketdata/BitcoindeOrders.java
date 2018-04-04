package org.knowm.xchange.bitcoinde.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BitcoindeOrders {

  private final BitcoindeOrder[] bids;
  private final BitcoindeOrder[] asks;

  public BitcoindeOrders(
      @JsonProperty("bids") BitcoindeOrder[] bids, @JsonProperty("asks") BitcoindeOrder[] asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public BitcoindeOrder[] getBids() {
    return bids;
  }

  public BitcoindeOrder[] getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "BitcoindeOrders{"
        + "bids="
        + Arrays.toString(bids)
        + ", asks="
        + Arrays.toString(asks)
        + '}';
  }
}
