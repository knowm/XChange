package org.knowm.xchange.binance.dto.trade.futures;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class BinanceSetLeverage {
  public final int leverage;
  public final String maxNotionalValue;
  public final String symbol;
}
