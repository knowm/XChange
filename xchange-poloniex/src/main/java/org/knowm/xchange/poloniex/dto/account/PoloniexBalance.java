package org.knowm.xchange.poloniex.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import javax.annotation.Generated;

/** Created by Roland Schumacher on 12.09.2015. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"available", "onOrders", "btcValue"})
public class PoloniexBalance {
  @JsonProperty("available")
  private BigDecimal available;

  @JsonProperty("onOrders")
  private BigDecimal onOrders;

  @JsonProperty("btcValue")
  private BigDecimal btcValue;

  @JsonProperty("available")
  public BigDecimal getAvailable() {
    return available;
  }

  @JsonProperty("available")
  public void setAvailable(BigDecimal available) {
    this.available = available;
  }

  @JsonProperty("onOrders")
  public BigDecimal getOnOrders() {
    return onOrders;
  }

  @JsonProperty("onOrders")
  public void setOnOrders(BigDecimal onOrders) {
    this.onOrders = onOrders;
  }

  @JsonProperty("btcValue")
  public BigDecimal getBtcValue() {
    return btcValue;
  }

  @JsonProperty("btcValue")
  public void setBtcValue(BigDecimal btcValue) {
    this.btcValue = btcValue;
  }

  @Override
  public String toString() {
    return "PoloniexBalance["
        + "available="
        + available
        + ", onOrders="
        + onOrders
        + ", btcValue="
        + btcValue
        + ']';
  }
}
