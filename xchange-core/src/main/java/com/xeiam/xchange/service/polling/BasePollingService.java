package com.xeiam.xchange.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;

public interface BasePollingService {
  /**
   * @deprecated use {@link com.xeiam.xchange.Exchange#getMetaData} and {@link ExchangeMetaData#getMarketMetaDataMap()}
   */
  @Deprecated
  List<CurrencyPair> getExchangeSymbols() throws IOException;

}
