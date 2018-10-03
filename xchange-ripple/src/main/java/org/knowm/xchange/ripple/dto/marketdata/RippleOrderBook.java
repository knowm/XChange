package org.knowm.xchange.ripple.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.ripple.dto.RippleCommon;

public final class RippleOrderBook extends RippleCommon {

  @JsonProperty("order_book")
  private String orderBook;

  @JsonProperty("bids")
  private List<RippleOrder> bids = new ArrayList<>();

  @JsonProperty("asks")
  private List<RippleOrder> asks = new ArrayList<>();

  public String getOrderBook() {
    return orderBook;
  }

  public void setOrderBook(final String orderBook) {
    this.orderBook = orderBook;
  }

  public List<RippleOrder> getBids() {
    return bids;
  }

  public void setBids(final List<RippleOrder> bids) {
    this.bids = bids;
  }

  public List<RippleOrder> getAsks() {
    return asks;
  }

  public void setAsks(final List<RippleOrder> asks) {
    this.asks = asks;
  }

  @Override
  public String toString() {
    return String.format(
        "OrderBook [ledger=%s, validated=%b, success=%b, order_book=%s, bids=%s, asks=%s]",
        ledger, validated, success, orderBook, bids, asks);
  }
}
