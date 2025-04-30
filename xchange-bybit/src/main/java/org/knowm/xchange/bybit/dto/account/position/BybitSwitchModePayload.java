package org.knowm.xchange.bybit.dto.account.position;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BybitSwitchModePayload {
  private String category;
  private String symbol;
  private String coin;
  private int mode;
}
