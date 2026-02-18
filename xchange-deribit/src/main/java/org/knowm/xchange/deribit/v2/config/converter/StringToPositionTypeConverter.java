package org.knowm.xchange.deribit.v2.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Locale;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.OpenPosition.Type;

/** Converts string to {@code OpenPosition.Type} */
public class StringToPositionTypeConverter extends StdConverter<String, OpenPosition.Type> {

  @Override
  public OpenPosition.Type convert(String value) {
    switch (value.toUpperCase(Locale.ROOT)) {
      case "BUY":
        return Type.LONG;
      case "SELL":
        return Type.SHORT;
      case "ZERO":
        return null;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
