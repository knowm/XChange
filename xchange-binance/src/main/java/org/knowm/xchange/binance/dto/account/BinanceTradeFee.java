package org.knowm.xchange.binance.dto.account;

import lombok.Getter;

@Getter
public class BinanceTradeFee {

  private String symbol;
  private String makerCommission;
  private String takerCommission;
}
