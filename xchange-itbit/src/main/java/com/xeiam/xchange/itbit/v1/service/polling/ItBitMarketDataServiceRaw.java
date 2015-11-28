package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTrade;

public class ItBitMarketDataServiceRaw extends ItBitBasePollingService {

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

  public ItBitTrade[] getItBitTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }

    ItBitTrade[] trades = itBitPublic.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), since);
    return trades;
  }
}
