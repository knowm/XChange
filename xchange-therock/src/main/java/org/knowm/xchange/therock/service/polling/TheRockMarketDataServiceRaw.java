package org.knowm.xchange.therock.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.marketdata.TheRockOrderBook;
import org.knowm.xchange.therock.dto.marketdata.TheRockTicker;
import org.knowm.xchange.therock.dto.marketdata.TheRockTrades;

import si.mazi.rescu.RestProxyFactory;

public class TheRockMarketDataServiceRaw extends TheRockBasePollingService {

  private final TheRock theRock;

  public TheRockMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.theRock = RestProxyFactory.createProxy(TheRock.class, exchange.getExchangeSpecification().getSslUri());
  }

  public TheRockTicker getTheRockTicker(TheRock.Pair currencyPair) throws TheRockException, IOException {
    return theRock.getTicker(currencyPair);
  }

  public TheRockOrderBook getTheRockOrderBook(TheRock.Pair currencyPair) throws TheRockException, IOException {
    return theRock.getOrderbook(currencyPair);
  }

  public TheRockTrades getTheRockTrades(TheRock.Pair currencyPair, Object[] args) throws IOException {
    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }

    TheRockTrades trades = new TheRockTrades(theRock.getTrades(currencyPair, since));
    return trades;
  }
}
