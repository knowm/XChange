package org.knowm.xchange.coinex.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinexMarketType {

  SPOT,
  MARGIN,
  FUTURES;

}
