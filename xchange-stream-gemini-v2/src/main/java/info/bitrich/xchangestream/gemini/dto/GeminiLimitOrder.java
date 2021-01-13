package info.bitrich.xchangestream.gemini.dto;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLevel;

import java.math.BigDecimal;

/**
 * Adapted from V1 by Max Gao on 01-09-2021
 */
public class GeminiLimitOrder extends GeminiLevel {
  private final Order.OrderType side;

  public GeminiLimitOrder(
      Order.OrderType side, BigDecimal price, BigDecimal amount, BigDecimal timestamp) {
    super(price, amount, timestamp);
    this.side = side;
  }

  public Order.OrderType getSide() {
    return side;
  }
}
