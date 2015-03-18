package com.xeiam.xchange.loyalbit.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.loyalbit.LoyalbitAdapters;
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
    throw new NotYetImplementedForExchangeException();
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
