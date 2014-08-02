package com.xeiam.xchange.poloniex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.poloniex.PoloniexAdapters;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexDepth;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexTicker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author Zach Holmes
 */

public class PoloniexMarketDataService extends PoloniexMarketDataServiceRaw implements PollingMarketDataService {

  public PoloniexMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    PoloniexTicker poloniexTicker = getPoloniexTicker(currencyPair);
    Ticker ticker = PoloniexAdapters.adaptPoloniexTicker(poloniexTicker, currencyPair);
    return ticker;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    PoloniexDepth depth = getPoloniexDepth(currencyPair);
    OrderBook orderBook = PoloniexAdapters.adaptPoloniexDepth(depth, currencyPair);
    return orderBook;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    PoloniexPublicTrade[] poloniexPublicTrades = getPoloniexPublicTrades(currencyPair);
    return PoloniexAdapters.adaptPoloniexPublicTrades(poloniexPublicTrades, currencyPair);
  }

}
