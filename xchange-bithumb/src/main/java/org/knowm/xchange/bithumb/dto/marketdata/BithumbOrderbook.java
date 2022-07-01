package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BithumbOrderbook {
  private final String paymentCurrency;
  private final String orderCurrency;
  private final List<BithumbOrderbookEntry> bids;
  private final List<BithumbOrderbookEntry> asks;
  private final long timestamp;

  public BithumbOrderbook(
      @JsonProperty("payment_currency") String paymentCurrency,
      @JsonProperty("order_currency") String orderCurrency,
      @JsonProperty("bids") List<BithumbOrderbookEntry> bids,
      @JsonProperty("asks") List<BithumbOrderbookEntry> asks,
      @JsonProperty("timestamp") long timestamp) {
    this.paymentCurrency = paymentCurrency;
    this.orderCurrency = orderCurrency;
    this.bids = bids;
    this.asks = asks;
    this.timestamp = timestamp;
  }

  public String getPaymentCurrency() {
    return paymentCurrency;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public List<BithumbOrderbookEntry> getBids() {
    return bids;
  }

  public List<BithumbOrderbookEntry> getAsks() {
    return asks;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "BithumbOrderbook{"
        + "paymentCurrency='"
        + paymentCurrency
        + '\''
        + ", orderCurrency='"
        + orderCurrency
        + '\''
        + ", bids="
        + bids
        + ", asks="
        + asks
        + ", timestamp="
        + timestamp
        + '}';
  }
}
