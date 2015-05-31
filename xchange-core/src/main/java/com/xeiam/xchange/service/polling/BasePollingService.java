package com.xeiam.xchange.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;

public interface BasePollingService {
  /**
   * @deprecated use {@link com.xeiam.xchange.Exchange#getMetaData} and {@link com.xeiam.xchange.dto.meta.SimpleMetaData#getCurrencyPairs}
   * or {@link com.xeiam.xchange.dto.meta.ExchangeMetaData#getCurrencyPairs}
   */
  @Deprecated
  public List<CurrencyPair> getExchangeSymbols() throws IOException;

}
