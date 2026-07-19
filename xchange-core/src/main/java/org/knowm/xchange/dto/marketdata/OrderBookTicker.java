package org.knowm.xchange.dto.marketdata;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderBookTicker {
  final private long timestamp;
  final private BigDecimal bidPrice;
  final private BigDecimal askPrice;
  final private BigDecimal bidSize;
  final private BigDecimal askSize;

}
