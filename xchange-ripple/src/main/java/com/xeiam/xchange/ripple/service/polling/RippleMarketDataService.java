package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.ripple.RippleAdapters;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.service.polling.params.RippleMarketDataParams;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class RippleMarketDataService extends RippleMarketDataServiceRaw implements PollingMarketDataService {

  public RippleMarketDataService(final Exchange exchange) {
    super(exchange);
  }

  /**
   * If the base currency is not XRP then the returned orders' additional data map contains a value for {@link RippleExchange.DATA_BASE_COUNTERPARTY},
   * similarly if the counter currency is not XRP then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} is populated.
   * 
   * @param currencyPair the base/counter currency pair
   * @param args a RippleMarketDataParams object needs to be supplied
   */
  @Override
  public OrderBook getOrderBook(final CurrencyPair currencyPair, final Object... args) throws IOException {
    if ((args.length > 0) && (args[0] instanceof RippleMarketDataParams)) {
      final RippleMarketDataParams params = (RippleMarketDataParams) args[0];
      final RippleOrderBook orderBook = getRippleOrderBook(currencyPair, params);
      return RippleAdapters.adaptOrderBook(orderBook, params, currencyPair);
    } else {
      throw new ExchangeException("RippleMarketDataParams is missing");
    }
  }

  @Override
  public Ticker getTicker(final CurrencyPair currencyPair, final Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Trades getTrades(final CurrencyPair currencyPair, final Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
