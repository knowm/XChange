package org.knowm.xchange.ripple.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ripple.RippleAdapters;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrderBook;
import org.knowm.xchange.ripple.service.params.RippleMarketDataParams;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class RippleMarketDataService extends RippleMarketDataServiceRaw
    implements MarketDataService {

  public RippleMarketDataService(final Exchange exchange) {
    super(exchange);
  }

  /**
   * If the base currency is not XRP then the returned orders' additional data map contains a value
   * for {@link RippleExchange.DATA_BASE_COUNTERPARTY}, similarly if the counter currency is not XRP
   * then {@link RippleExchange.DATA_COUNTER_COUNTERPARTY} is populated.
   *
   * @param currencyPair the base/counter currency pair
   * @param args a RippleMarketDataParams object needs to be supplied
   */
  @Override
  public OrderBook getOrderBook(final CurrencyPair currencyPair, final Object... args)
      throws IOException {
    if ((args != null && args.length > 0) && (args[0] instanceof RippleMarketDataParams)) {
      final RippleMarketDataParams params = (RippleMarketDataParams) args[0];
      final RippleOrderBook orderBook = getRippleOrderBook(currencyPair, params);
      return RippleAdapters.adaptOrderBook(orderBook, params, currencyPair);
    } else {
      throw new ExchangeException("RippleMarketDataParams is missing");
    }
  }
}
