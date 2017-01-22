package org.knowm.xchange.itbit.v1.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTrades;

public class ItBitMarketDataServiceRaw extends ItBitBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public ItBitTicker getItBitTicker(CurrencyPair currencyPair) throws IOException {

    ItBitTicker ticker = itBitAuthenticated.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return ticker;
  }

  public ItBitDepth getItBitDepth(CurrencyPair currencyPair, Object... args) throws IOException {
    ItBitDepth depth = itBitPublic.getDepth(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return depth;
  }

  public ItBitTrades getItBitTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }

    ItBitTrades trades = itBitPublic.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), since);
    return trades;
  }
}
