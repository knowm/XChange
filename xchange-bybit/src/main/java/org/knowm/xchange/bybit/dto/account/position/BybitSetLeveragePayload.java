package org.knowm.xchange.bybit.dto.account.position;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BybitSetLeveragePayload {
  private String category;
  private String symbol;
  private String buyLeverage;
  private String sellLeverage;
}
