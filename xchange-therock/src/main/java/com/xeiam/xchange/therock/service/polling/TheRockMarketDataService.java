package com.xeiam.xchange.therock.service.polling;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.therock.TheRock;
import com.xeiam.xchange.therock.TheRockAdapters;
import com.xeiam.xchange.therock.dto.marketdata.TheRockOrderBook;
import com.xeiam.xchange.therock.dto.marketdata.TheRockTicker;

/**
 * @author Matija Mazi
 */
public class TheRockMarketDataService extends TheRockMarketDataServiceRaw implements PollingMarketDataService {

  public TheRockMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    TheRockTicker t = getTheRockTicker(new TheRock.Pair(currencyPair));
    return new Ticker.Builder().currencyPair(currencyPair).last(t.getLast()).bid(t.getBid()).ask(t.getAsk()).high(t.getHigh()).low(t.getLow())
        .volume(t.getVolume()).timestamp(new Date()).build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    final TheRockOrderBook theRockOrderBook = getTheRockOrderBook(new TheRock.Pair(currencyPair));
    return TheRockAdapters.adaptOrderBook(theRockOrderBook);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
