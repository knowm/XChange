package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.marketdata.TheRockOrderBook;
import org.knowm.xchange.therock.dto.marketdata.TheRockTicker;
import org.knowm.xchange.therock.dto.marketdata.TheRockTrades;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class TheRockMarketDataServiceRaw extends TheRockBaseService {

  private final TheRock theRock;

  public TheRockMarketDataServiceRaw(Exchange exchange) {
    super(exchange);

    // allow HTTP connect- and read-timeout to be set per exchange
    ClientConfig rescuConfig = new ClientConfig(); // default rescu config
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }

    this.theRock = RestProxyFactory.createProxy(TheRock.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
  }

  public TheRockTicker getTheRockTicker(TheRock.Pair currencyPair) throws TheRockException, IOException {
    return theRock.getTicker(currencyPair);
  }

  public TheRockOrderBook getTheRockOrderBook(TheRock.Pair currencyPair) throws TheRockException, IOException {
    return theRock.getOrderbook(currencyPair);
  }

  public TheRockTrades getTheRockTrades(TheRock.Pair currencyPair, Object[] args) throws IOException {
    Date after = null;
    if (args.length == 1) {
      Object arg = args[0];
      if (arg instanceof Number) {
        after = new Date(((Number) arg).longValue() * 1000);
      } else if (arg instanceof Date) {
        after = (Date) arg;
      }

    }
    return theRock.getTrades(currencyPair, after);
  }
}
