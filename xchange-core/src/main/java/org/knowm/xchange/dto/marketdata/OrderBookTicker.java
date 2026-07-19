package org.knowm.xchange.dto.marketdata;

import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class OrderBookTicker {
  private long timestamp;
  private BigDecimal bidPrice;
  private BigDecimal askPrice;
  private BigDecimal bidSize;
  private BigDecimal askSize;

}
