package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.quoine.QuoineAdapters;
import com.xeiam.xchange.quoine.QuoineUtils;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineOrderBook;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineProduct;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class QuoineMarketDataService extends QuoineMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuoineMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    QuoineProduct quoineTicker = getQuoineProduct(QuoineUtils.toPairString(currencyPair));
    return QuoineAdapters.adaptTicker(quoineTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    QuoineOrderBook quoineOrderBook = getOrderBook(QuoineUtils.toID(currencyPair));
    return QuoineAdapters.adaptOrderBook(quoineOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
