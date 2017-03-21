package org.knowm.xchange.loyalbit.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.loyalbit.LoyalbitAdapters;
import org.knowm.xchange.loyalbit.dto.marketdata.LoyalbitTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author Matija Mazi
 */
public class LoyalbitMarketDataService extends LoyalbitMarketDataServiceRaw implements MarketDataService {

  public LoyalbitMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    LoyalbitTicker t = getLoyalbitTicker();
    return new Ticker.Builder().currencyPair(currencyPair).last(t.last).bid(t.bid).ask(t.ask).high(t.high).low(t.low).volume(t.volume)
        .timestamp(new Date()).build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return LoyalbitAdapters.adaptOrderBook(getLoyalbitOrderBook(), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
