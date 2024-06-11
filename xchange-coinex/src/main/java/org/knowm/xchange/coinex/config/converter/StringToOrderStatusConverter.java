package org.knowm.xchange.coinex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.dto.Order.OrderStatus;

/**
 * Converts string to {@code OrderStatus}
 */
public class StringToOrderStatusConverter extends StdConverter<String, OrderStatus> {

  @Override
  public OrderStatus convert(String value) {
    switch (value) {
      case "open":
        return OrderStatus.OPEN;
      case "part_filled":
        return OrderStatus.PARTIALLY_FILLED;
      case "filled":
        return OrderStatus.FILLED;
      case "part_canceled":
        return OrderStatus.PARTIALLY_CANCELED;
      case "canceled":
        return OrderStatus.CANCELED;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
