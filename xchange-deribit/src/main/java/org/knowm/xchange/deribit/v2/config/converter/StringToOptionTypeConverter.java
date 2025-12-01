package org.knowm.xchange.deribit.v2.config.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Locale;
import org.knowm.xchange.derivative.OptionsContract.OptionType;

/** Converts string to {@code OptionType} */
public class StringToOptionTypeConverter extends StdConverter<String, OptionType> {

  @Override
  public OptionType convert(String value) {
    switch (value.toUpperCase(Locale.ROOT)) {
      case "PUT":
        return OptionType.PUT;
      case "CALL":
        return OptionType.CALL;
      default:
        throw new IllegalArgumentException("Can't map " + value);
    }
  }
}
