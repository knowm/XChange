package org.knowm.xchange.bitfinex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.dto.Order.OrderStatus;

/** Converts string to {@code OrderStatus} */
@Slf4j
public class StringToOrderStatusConverter extends StdConverter<String, OrderStatus> {

  @Override
  public OrderStatus convert(String value) {
    OrderStatus result = null;

    if (value.startsWith("EXECUTED")) {
      result = OrderStatus.FILLED;

    } else if (value.contains("PARTIALLY FILLED")) {
      result = OrderStatus.PARTIALLY_FILLED;

    } else if (value.endsWith("CANCELED")) {
      result = OrderStatus.CANCELED;

    } else {
      switch (value) {
        case "ACTIVE":
          result = OrderStatus.OPEN;
          break;
        case "FORCED EXECUTED":
          result = OrderStatus.FILLED;
          break;
        case "INSUFFICIENT BALANCE (U1)":
        case "RSN_POS_REDUCE_INCR":
        case "RSN_POS_REDUCE_FLIP":
        case "RSN_PAUSE":
          result = OrderStatus.CANCELED;
          break;
        case "PARTIALLY FILLED":
        case "INSUFFICIENT BALANCE (G1)":
          result = OrderStatus.PARTIALLY_FILLED;
          break;
        default:
      }
    }

    log.info("Converted {} into {}", value, result);

    if (result == null) {
      throw new IllegalArgumentException("Can't map " + value);
    }

    return result;
  }
}
