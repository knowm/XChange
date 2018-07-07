package org.knowm.xchange.bitfinex.v2.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v2.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.currency.CurrencyPair;

public class BitfinexMarketDataServiceRaw extends BitfinexBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitfinexTicker[] getBitfinexTickers(List<CurrencyPair> currencyPairs) throws IOException {
    return bitfinex.getTickers(BitfinexAdapters.adaptCurrencyPairsToTickersParam(currencyPairs));
  }
}
