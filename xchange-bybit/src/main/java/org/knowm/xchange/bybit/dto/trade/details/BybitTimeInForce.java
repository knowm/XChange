package org.knowm.xchange.bybit.dto.trade.details;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.dto.Order.IOrderFlags;

@Getter
@AllArgsConstructor
public enum BybitTimeInForce implements IOrderFlags {
  GTC("GTC"),
  IOC("IOC"),
  FOK("FOK"),
  POSTONLY("PostOnly");
  @JsonValue
  private final String value;
}


