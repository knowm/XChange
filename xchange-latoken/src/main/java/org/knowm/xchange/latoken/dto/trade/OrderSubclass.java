package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum OrderSubclass {
  Limit;

  @JsonCreator
  public static OrderSubclass parse(String s) {
    try {
      return OrderSubclass.valueOf(StringUtils.capitalize(s));
    } catch (Exception e) {
      throw new RuntimeException("Unknown OrderSubclass " + s + ".");
    }
  }
}
