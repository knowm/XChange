package org.knowm.xchange.latoken.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.StringUtils;

public enum LatokenOrderStatus {
  Active,
  PartiallyFilled,
  Filled,
  Cancelled;

  @JsonCreator
  public static LatokenOrderStatus parse(String s) {
    try {
      return LatokenOrderStatus.valueOf(StringUtils.capitalize(s));
    } catch (Exception e) {
      throw new RuntimeException("Unknown LatokenOrderStatus " + s + ".");
    }
  }
}
