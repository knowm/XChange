package org.knowm.xchange.gateio.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.dto.Order.OrderType;

/**
 * Converts {@code OrderType} to string
 */
public class OrderTypeToStringConverter extends StdConverter<OrderType, String> {

  @Override
  public String convert(OrderType value) {
    return switch (value) {
      case BID, EXIT_ASK -> "buy";
      case ASK, EXIT_BID -> "sell";
    };
  }
}
