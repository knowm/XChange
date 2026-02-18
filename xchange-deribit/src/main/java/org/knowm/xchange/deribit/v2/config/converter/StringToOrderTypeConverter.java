package org.knowm.xchange.deribit.v2.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Locale;
import org.knowm.xchange.dto.Order.OrderType;

/** Converts string to {@code OrderType} */
public class StringToOrderTypeConverter extends StdConverter<String, OrderType> {

  @Override
  public OrderType convert(String value) {
    switch (value.toUpperCase(Locale.ROOT)) {
      case "BUY":
        return OrderType.BID;
      case "SELL":
        return OrderType.ASK;
      case "ZERO":
        return null;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
