package org.knowm.xchange.binance.dto.account.futures;

import lombok.Getter;

@Getter
public class BinanceFutureCommissionRate {

  private String symbol;
  private String makerCommissionRate;
  private String takerCommissionRate;
}
