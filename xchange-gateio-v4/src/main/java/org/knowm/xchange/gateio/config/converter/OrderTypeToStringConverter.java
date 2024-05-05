package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.dto.Order.OrderType;

/**
 * Converts {@code OrderType} to string
 */
public class OrderTypeToStringConverter extends StdConverter<OrderType, String> {

  @Override
  public String convert(OrderType value) {
    switch (value) {
      case BID:
        return "buy";
      case ASK:
        return "sell";
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
