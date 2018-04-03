package org.knowm.xchange.itbit.v1.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.itbit.v1.ItBitAdapters;
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

    CurrencyPair exchangePair = ItBitAdapters.adaptCurrencyPairToExchange(currencyPair);
    ItBitTicker ticker =
        itBitAuthenticated.getTicker(
            exchangePair.base.getCurrencyCode(), exchangePair.counter.getCurrencyCode());

    return ticker;
  }

  public ItBitDepth getItBitDepth(CurrencyPair currencyPair, Object... args) throws IOException {

    CurrencyPair exchangePair = ItBitAdapters.adaptCurrencyPairToExchange(currencyPair);
    ItBitDepth depth =
        itBitPublic.getDepth(
            exchangePair.base.getCurrencyCode(), exchangePair.counter.getCurrencyCode());

    return depth;
  }

  public ItBitTrades getItBitTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }

    CurrencyPair exchangePair = ItBitAdapters.adaptCurrencyPairToExchange(currencyPair);
    ItBitTrades trades =
        itBitPublic.getTrades(
            exchangePair.base.getCurrencyCode(), exchangePair.counter.getCurrencyCode(), since);
    return trades;
  }
}
