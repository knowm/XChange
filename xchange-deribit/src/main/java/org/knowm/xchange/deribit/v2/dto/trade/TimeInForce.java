package org.knowm.xchange.deribit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.knowm.xchange.dto.Order.IOrderFlags;

public enum TimeInForce implements IOrderFlags {
  good_til_cancelled,
  good_til_day,
  fill_or_kill,
  immediate_or_cancel;

  @JsonCreator
  public static TimeInForce parseTimeInForce(String s) {
    try {
      return TimeInForce.valueOf(s);
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to parse time_in_force: \"" + s + "\"");
    }
  }
}
