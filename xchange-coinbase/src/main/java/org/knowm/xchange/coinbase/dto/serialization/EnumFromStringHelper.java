package org.knowm.xchange.coinbase.dto.serialization;

import java.util.HashMap;
import java.util.Map;

/** @author jamespedwards42 */
public class EnumFromStringHelper<T extends Enum<T>> {

  private final Map<String, T> fromString = new HashMap<>();

  public EnumFromStringHelper(Class<T> enumClass) {

    for (T enumVal : enumClass.getEnumConstants())
      fromString.put(enumVal.toString().toLowerCase(), enumVal);
  }

  public EnumFromStringHelper<T> addJsonStringMapping(String jsonString, final T enumVal) {

    fromString.put(jsonString, enumVal);
    return this;
  }

  public T fromJsonString(String jsonString) {

    return fromString.get(jsonString.toLowerCase());
  }
}
