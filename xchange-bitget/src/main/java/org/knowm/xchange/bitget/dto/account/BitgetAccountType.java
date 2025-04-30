package org.knowm.xchange.bitget.dto.account;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BitgetAccountType {
  SPOT("spot"),
  P2P("p2p"),
  COIN_FUTURES("coin_futures"),
  USDT_FUTURES("usdt_futures"),
  USDC_FUTURES("usdc_futures"),
  CROSSED_MARGIN("crossed_margin"),
  ISOLATED_MARGIN("isolated_margin");

  @JsonValue private final String value;
}
