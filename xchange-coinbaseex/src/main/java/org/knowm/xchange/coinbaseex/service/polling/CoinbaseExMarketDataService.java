package org.knowm.xchange.coinbaseex.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbaseex.CoinbaseExAdapters;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExMarketDataService extends CoinbaseExMarketDataServiceRaw implements PollingMarketDataService {

  public CoinbaseExMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    CoinbaseExProductTicker ticker = getCoinbaseExProductTicker(currencyPair);
    CoinbaseExProductStats stats = getCoinbaseExProductStats(currencyPair);
    CoinbaseExProductBook book = getCoinbaseExProductOrderBook(currencyPair);

    // Adapt to XChange DTOs
    return CoinbaseExAdapters.adaptTicker(ticker, stats, book, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return CoinbaseExAdapters.adaptOrderBook(getCoinbaseExProductOrderBook(currencyPair, 2), currencyPair);

  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return CoinbaseExAdapters.adaptTrades(getCoinbaseExTrades(currencyPair, 100), currencyPair);
  }

}
