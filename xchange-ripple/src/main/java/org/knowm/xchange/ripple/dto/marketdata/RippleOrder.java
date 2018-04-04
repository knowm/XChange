package org.knowm.xchange.ripple.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ripple.dto.RippleAmount;

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

  public RippleAmount getPrice() {
    return price;
  }

  public void setPrice(final RippleAmount value) {
    price = value;
  }

  public RippleAmount getTakerGetsFunded() {
    return takerGetsFunded;
  }

  public void setTakerGetsFunded(final RippleAmount value) {
    takerGetsFunded = value;
  }

  public RippleAmount getTakerGetsTotal() {
    return takerGetsTotal;
  }

  public void setTakerGetsTotal(final RippleAmount value) {
    takerGetsTotal = value;
  }

  public RippleAmount getTakerPaysFunded() {
    return takerPaysFunded;
  }

  public void setTakerPaysFunded(final RippleAmount value) {
    takerPaysFunded = value;
  }

  public RippleAmount getTakerPaysTotal() {
    return takerPaysTotal;
  }

  public void setTakerPaysTotal(final RippleAmount value) {
    takerPaysTotal = value;
  }

  public String getOrderMaker() {
    return orderMaker;
  }

  public void setOrderMaker(final String value) {
    orderMaker = value;
  }

  public Integer getSequence() {
    return sequence;
  }

  public void setSequence(final Integer value) {
    sequence = value;
  }

  public Boolean getPassive() {
    return passive;
  }

  public void setPassive(final Boolean value) {
    passive = value;
  }

  public Boolean getSell() {
    return sell;
  }

  public void setSell(final Boolean value) {
    sell = value;
  }

  @Override
  public String toString() {
    return String.format(
        "Order [order_maker=%s, sequence=%d, passive=%b, sell=%s, price=%s, taker_gets_funded=%s, taker_gets_total=%s, taker_pays_funded=%s, taker_pays_total=%s]",
        orderMaker,
        sequence,
        passive,
        sell,
        price,
        takerGetsFunded,
        takerGetsTotal,
        takerPaysFunded,
        takerPaysTotal);
  }
}
