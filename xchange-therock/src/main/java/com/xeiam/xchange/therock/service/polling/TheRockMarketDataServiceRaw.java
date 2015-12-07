package com.xeiam.xchange.therock.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.therock.TheRock;
import com.xeiam.xchange.therock.dto.TheRockException;
import com.xeiam.xchange.therock.dto.marketdata.TheRockOrderBook;
import com.xeiam.xchange.therock.dto.marketdata.TheRockTicker;

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
}
