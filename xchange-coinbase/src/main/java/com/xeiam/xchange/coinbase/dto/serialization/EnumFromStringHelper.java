package com.xeiam.xchange.coinbase.dto.serialization;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jamespedwards42
 */
public class EnumFromStringHelper<T extends Enum<T>> {

  private final Map<String, T> fromString = new HashMap<String, T>();

  public EnumFromStringHelper(Class<T> enumClass) {

    for (final T enumVal : enumClass.getEnumConstants())
      fromString.put(enumVal.toString().toLowerCase(), enumVal);
  }

  public EnumFromStringHelper<T> addJsonStringMapping(final String jsonString, final T enumVal) {

    fromString.put(jsonString, enumVal);
    return this;
  }

  public T fromJsonString(final String jsonString) {

    return fromString.get(jsonString.toLowerCase());
  }
}
