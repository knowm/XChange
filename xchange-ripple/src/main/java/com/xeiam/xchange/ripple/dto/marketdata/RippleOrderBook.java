package com.xeiam.xchange.ripple.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RippleOrderBook {

  @JsonProperty("order_book")
  private String orderBook;
  @JsonProperty("ledger")
  private String ledger;
  @JsonProperty("validated")
  private Boolean validated;
  @JsonProperty("bids")
  private List<RippleOrder> bids = new ArrayList<RippleOrder>();
  @JsonProperty("asks")
  private List<RippleOrder> asks = new ArrayList<RippleOrder>();
  @JsonProperty("success")
  private Boolean success;

  public String getOrderBook() {
    return orderBook;
  }

  public void setOrderBook(final String orderBook) {
    this.orderBook = orderBook;
  }

  public String getLedger() {
    return ledger;
  }

  public void setLedger(final String ledger) {
    this.ledger = ledger;
  }

  public Boolean getValidated() {
    return validated;
  }

  public void setValidated(final Boolean validated) {
    this.validated = validated;
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

  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(final Boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return String.format("OrderBook [order_book=%s, ledger=%s, validated=%s, success=%s, bids=%s, asks=%s]", //
        orderBook, ledger, validated, success, bids, asks);
  }
}