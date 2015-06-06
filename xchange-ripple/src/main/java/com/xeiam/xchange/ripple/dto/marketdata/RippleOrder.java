package com.xeiam.xchange.ripple.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "price", "taker_gets_funded", "taker_gets_total", "taker_pays_funded", "taker_pays_total", "order_maker", "sequence", "passive",
    "sell" })
public final class RippleOrder {

  @JsonProperty("price")
  private RippleAmount price;
  @JsonProperty("taker_gets_funded")
  private RippleAmount takerGetsFunded;
  @JsonProperty("taker_gets_total")
  private RippleAmount takerGetsTotal;
  @JsonProperty("taker_pays_funded")
  private RippleAmount takerPaysFunded;
  @JsonProperty("taker_pays_total")
  private RippleAmount takerPaysTotal;
  @JsonProperty("order_maker")
  private String orderMaker;
  @JsonProperty("sequence")
  private Integer sequence;
  @JsonProperty("passive")
  private Boolean passive;
  @JsonProperty("sell")
  private Boolean sell;

  @JsonProperty("price")
  public RippleAmount getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(final RippleAmount value) {
    price = value;
  }

  @JsonProperty("taker_gets_funded")
  public RippleAmount getTakerGetsFunded() {
    return takerGetsFunded;
  }

  @JsonProperty("taker_gets_funded")
  public void setTakerGetsFunded(final RippleAmount value) {
    takerGetsFunded = value;
  }

  @JsonProperty("taker_gets_total")
  public RippleAmount getTakerGetsTotal() {
    return takerGetsTotal;
  }

  @JsonProperty("taker_gets_total")
  public void setTakerGetsTotal(final RippleAmount value) {
    takerGetsTotal = value;
  }

  @JsonProperty("taker_pays_funded")
  public RippleAmount getTakerPaysFunded() {
    return takerPaysFunded;
  }

  @JsonProperty("taker_pays_funded")
  public void setTakerPaysFunded(final RippleAmount value) {
    takerPaysFunded = value;
  }

  @JsonProperty("taker_pays_total")
  public RippleAmount getTakerPaysTotal() {
    return takerPaysTotal;
  }

  @JsonProperty("taker_pays_total")
  public void setTakerPaysTotal(final RippleAmount value) {
    takerPaysTotal = value;
  }

  @JsonProperty("order_maker")
  public String getOrderMaker() {
    return orderMaker;
  }

  @JsonProperty("order_maker")
  public void setOrderMaker(final String value) {
    orderMaker = value;
  }

  @JsonProperty("sequence")
  public Integer getSequence() {
    return sequence;
  }

  @JsonProperty("sequence")
  public void setSequence(final Integer value) {
    sequence = value;
  }

  @JsonProperty("passive")
  public Boolean getPassive() {
    return passive;
  }

  @JsonProperty("passive")
  public void setPassive(final Boolean value) {
    passive = value;
  }

  @JsonProperty("sell")
  public Boolean getSell() {
    return sell;
  }

  @JsonProperty("sell")
  public void setSell(final Boolean value) {
    sell = value;
  }

  @Override
  public String toString() {
    return String
        .format(
            "Order [order_maker=%s, sequence=%s, passive=%s, sell=%s, price=%s, taker_gets_funded=%s, taker_gets_total=%s, taker_pays_funded=%s, taker_pays_total=%s]",
            orderMaker, sequence, passive, sell, price, takerGetsFunded, takerGetsTotal, takerPaysFunded, takerPaysTotal);
  }
}