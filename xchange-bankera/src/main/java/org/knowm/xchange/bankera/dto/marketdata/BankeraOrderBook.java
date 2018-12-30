package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BankeraOrderBook {

  private final List<OrderBookOrder> bids;
  private final List<OrderBookOrder> asks;

  public BankeraOrderBook(
      @JsonProperty("bids") List<OrderBookOrder> bids,
      @JsonProperty("asks") List<OrderBookOrder> asks) {
    this.asks = asks;
    this.bids = bids;
  }

  public List<OrderBookOrder> getBids() {
    return bids;
  }

  public List<OrderBookOrder> getAsks() {
    return asks;
  }

  public static final class OrderBookOrder {
    private final Integer id;
    private final String price;
    private final String amount;

    public OrderBookOrder(
        @JsonProperty("id") Integer id,
        @JsonProperty("price") String price,
        @JsonProperty("amount") String amount) {

      this.id = id;
      this.price = price;
      this.amount = amount;
    }

    public Integer getId() {
      return id;
    }

    public String getPrice() {
      return price;
    }

    public String getAmount() {
      return amount;
    }
  }
}
