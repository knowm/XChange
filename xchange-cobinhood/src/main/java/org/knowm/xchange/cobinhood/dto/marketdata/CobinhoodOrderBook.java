package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class CobinhoodOrderBook {
  private final List<List<BigDecimal>> asks;
  private final List<List<BigDecimal>> bids;

  public CobinhoodOrderBook(
      @JsonProperty("asks") List<List<BigDecimal>> asks,
      @JsonProperty("bids") List<List<BigDecimal>> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "CobinhoodOrderBook{" + "asks=" + asks + ", bids=" + bids + '}';
  }

  public static class Container {
    private final CobinhoodOrderBook orderBook;

    public Container(@JsonProperty("orderbook") CobinhoodOrderBook orderBook) {
      this.orderBook = orderBook;
    }

    public CobinhoodOrderBook getOrderBook() {
      return orderBook;
    }
  }
}
