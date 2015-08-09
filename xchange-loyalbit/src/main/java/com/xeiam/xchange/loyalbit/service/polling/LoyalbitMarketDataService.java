package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.loyalbit.LoyalbitAdapters;
import com.xeiam.xchange.loyalbit.dto.marketdata.LoyalbitTicker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Matija Mazi
 */
public class LoyalbitMarketDataService extends LoyalbitMarketDataServiceRaw implements PollingMarketDataService {

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
