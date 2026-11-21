package org.knowm.xchange.dto.marketdata;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Represents the current best bid and ask prices from the order book for a trading pair.
 */
@Getter
@Builder
public class OrderBookTicker {

  /** The timestamp of the order book ticker snapshot. */
  final private Instant timestamp;

  /** The best bid price. */
  final private BigDecimal bidPrice;

  /** The best ask price. */
  final private BigDecimal askPrice;

  /** The size available at the best bid price. */
  final private BigDecimal bidSize;

  /** The size available at the best ask price. */
  final private BigDecimal askSize;

}
