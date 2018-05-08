package org.knowm.xchange.vircurex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Created by David Henry on 2/20/14. */
public class VircurexOpenOrder {

  private final String orderId;
  private final String baseCurrency;
  private final String counterCurrency;
  private final BigDecimal openQuantity;
  private final BigDecimal quantity;
  private final BigDecimal unitPrice;
  private final String orderType;
  private final String lastChangedDate;
  private final String releaseDate;

  public VircurexOpenOrder(
      @JsonProperty("orderid") String orderId,
      @JsonProperty("ordertype") String orderType,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("openquantity") BigDecimal openQuantity,
      @JsonProperty("currency1") String baseCurrency,
      @JsonProperty("unitprice") BigDecimal unitPrice,
      @JsonProperty("currency2") String counterCurrency,
      @JsonProperty("lastchangedat") String lastChangedDate,
      @JsonProperty("releasedat") String releaseDate) {

    this.orderId = orderId;
    this.baseCurrency = baseCurrency;
    this.counterCurrency = counterCurrency;
    this.openQuantity = openQuantity;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.orderType = orderType;
    this.lastChangedDate = lastChangedDate;
    this.releaseDate = releaseDate;
  }

  public String getBaseCurrency() {

    return baseCurrency;
  }

  public String getCounterCurrency() {

    return counterCurrency;
  }

  public BigDecimal getOpenQuantity() {

    return openQuantity;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public String getOrderId() {

    return orderId;
  }

  public BigDecimal getUnitPrice() {

    return unitPrice;
  }

  public String getOrderType() {

    return orderType;
  }

  public String getLastChangedDate() {

    return lastChangedDate;
  }

  public String getReleaseDate() {

    return releaseDate;
  }
}
