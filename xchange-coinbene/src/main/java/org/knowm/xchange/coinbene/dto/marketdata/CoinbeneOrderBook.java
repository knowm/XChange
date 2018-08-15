package org.knowm.xchange.coinbene.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;

public class CoinbeneOrderBook {
  private final List<CoinbeneOrder> asks;
  private final List<CoinbeneOrder> bids;

  public CoinbeneOrderBook(
      @JsonProperty("asks") List<CoinbeneOrder> asks,
      @JsonProperty("bids") List<CoinbeneOrder> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public List<CoinbeneOrder> getAsks() {
    return asks;
  }

  public List<CoinbeneOrder> getBids() {
    return bids;
  }

  @Override
  public String toString() {
    return "CoinbeneOrderBook{" + "asks=" + asks + ", bids=" + bids + '}';
  }

  public static class Container extends CoinbeneResponse {
    private final CoinbeneOrderBook orderBook;
    private final String symbol;

    public Container(
        @JsonProperty("orderbook") CoinbeneOrderBook orderBook,
        @JsonProperty("symbol") String symbol) {
      this.orderBook = orderBook;
      this.symbol = symbol;
    }

    public CoinbeneOrderBook getOrderBook() {
      return orderBook;
    }

    public String getSymbol() {
      return symbol;
    }
  }
}
