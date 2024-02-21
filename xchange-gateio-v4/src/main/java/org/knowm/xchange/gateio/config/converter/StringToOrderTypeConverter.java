package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.dto.Order.OrderType;

/**
 * Converts string to {@code OrderType}
 */
public class StringToOrderTypeConverter extends StdConverter<String, OrderType> {

  @Override
  public OrderType convert(String value) {
    switch (value) {
      case "buy":
        return OrderType.BID;
      case "sell":
        return OrderType.ASK;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
