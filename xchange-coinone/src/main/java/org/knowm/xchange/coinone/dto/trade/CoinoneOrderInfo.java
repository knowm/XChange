package org.knowm.xchange.coinone.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinoneOrderInfo {

  private final BigDecimal price;
  private final Long timestamp;
  private final BigDecimal qty;
  private final BigDecimal remainQty;
  private final String type;
  private final String currency;
  private final BigDecimal feeRate;
  private final BigDecimal fee;
  private final String orderId;

  /**
   * @param price
   * @param timestamp
   * @param qty
   * @param remainQty
   * @param type
   * @param currency
   * @param feeRate
   * @param fee
   * @param orderId
   */
  public CoinoneOrderInfo(
      @JsonProperty("price") String price,
      @JsonProperty("timestamp") String timestamp,
      @JsonProperty("qty") String qty,
      @JsonProperty("remainQty") String remainQty,
      @JsonProperty("type") String type,
      @JsonProperty("currency") String currency,
      @JsonProperty("feeRate") String feeRate,
      @JsonProperty("fee") String fee,
      @JsonProperty("orderId") String orderId) {

    this.price = new BigDecimal(price);
    this.timestamp = Long.valueOf(timestamp);
    this.qty = new BigDecimal(qty);
    this.remainQty = new BigDecimal(remainQty);
    this.type = type;
    this.currency = currency;
    this.feeRate = new BigDecimal(feeRate);
    this.fee = new BigDecimal(fee);
    this.orderId = orderId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public BigDecimal getRemainQty() {
    return remainQty;
  }

  public String getType() {
    return type;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getFeeRate() {
    return feeRate;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getOrderId() {
    return orderId;
  }
}
