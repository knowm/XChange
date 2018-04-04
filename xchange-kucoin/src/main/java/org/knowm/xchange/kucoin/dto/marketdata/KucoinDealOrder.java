package org.knowm.xchange.kucoin.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import org.knowm.xchange.kucoin.dto.KucoinOrderType;

@JsonDeserialize(using = KucoinDealOrderDeserializer.class)
public class KucoinDealOrder {

  private Long timestamp;
  private KucoinOrderType orderType;
  private BigDecimal price;
  private BigDecimal amount;
  private BigDecimal volume;

  public KucoinDealOrder(
      Long timestamp,
      KucoinOrderType orderType,
      BigDecimal price,
      BigDecimal amount,
      BigDecimal volume) {

    this.timestamp = timestamp;
    this.orderType = orderType;
    this.price = price;
    this.amount = amount;
    this.volume = volume;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public KucoinOrderType getOrderType() {
    return orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getVolume() {
    return volume;
  }
}
