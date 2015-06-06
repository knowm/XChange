package com.xeiam.xchange.ripple.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "order_book", "ledger", "validated", "bids", "asks", "success" })
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

  @JsonProperty("order_book")
  public String getOrderBook() {
    return orderBook;
  }

  @JsonProperty("order_book")
  public void setOrderBook(final String orderBook) {
    this.orderBook = orderBook;
  }

  @JsonProperty("ledger")
  public String getLedger() {
    return ledger;
  }

  @JsonProperty("ledger")
  public void setLedger(final String ledger) {
    this.ledger = ledger;
  }

  @JsonProperty("validated")
  public Boolean getValidated() {
    return validated;
  }

  @JsonProperty("validated")
  public void setValidated(final Boolean validated) {
    this.validated = validated;
  }

  @JsonProperty("bids")
  public List<RippleOrder> getBids() {
    return bids;
  }

  @JsonProperty("bids")
  public void setBids(final List<RippleOrder> bids) {
    this.bids = bids;
  }

  @JsonProperty("asks")
  public List<RippleOrder> getAsks() {
    return asks;
  }

  @JsonProperty("asks")
  public void setAsks(final List<RippleOrder> asks) {
    this.asks = asks;
  }

  @JsonProperty("success")
  public Boolean getSuccess() {
    return success;
  }

  @JsonProperty("success")
  public void setSuccess(final Boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return String.format("OrderBook [order_book=%s, ledger=%s, validated=%s, success=%s, bids=%s, asks=%s]", //
        orderBook, ledger, validated, success, bids, asks);
  }
}