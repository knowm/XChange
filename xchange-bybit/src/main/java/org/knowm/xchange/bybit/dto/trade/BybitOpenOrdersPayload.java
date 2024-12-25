package org.knowm.xchange.bybit.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BybitOpenOrdersPayload {
  private final String category;
  private final String orderId;
}
