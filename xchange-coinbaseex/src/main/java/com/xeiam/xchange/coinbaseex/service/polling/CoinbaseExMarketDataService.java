package com.xeiam.xchange.coinbaseex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseExAdapters;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

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
