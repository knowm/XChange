package org.knowm.xchange.coinex.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.dto.Order.OrderType;

/** Converts {@code OrderType} to string */
public class OrderTypeToStringConverter extends StdConverter<OrderType, String> {

  @Override
  public String convert(OrderType value) {
    return CoinexAdapters.toString(value);
  }
}
