package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BTERPollingMarketDataService extends BTERPollingMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERPollingMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTicker ticker = super.getBTERTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return BTERAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERDepth bterDepth = super.getBTEROrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return BTERAdapters.adaptOrderBook(bterDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTradeHistory tradeHistory = (args != null && args.length > 0 && args[0] != null && args[0] instanceof String)
        ? super.getBTERTradeHistorySince(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), (String) args[0])
        : super.getBTERTradeHistory(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return BTERAdapters.adaptTrades(tradeHistory, currencyPair);
  }

}
