package org.knowm.xchange.bybit.dto.trade.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.dto.Order.IOrderFlags;

@Getter
@AllArgsConstructor
public enum BybitHedgeMode implements IOrderFlags {
  ONEWAY,
  TWOWAY;
}
