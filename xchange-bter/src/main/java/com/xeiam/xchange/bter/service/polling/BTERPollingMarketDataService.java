package com.xeiam.xchange.bter.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.BTERAdapters;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class BTERPollingMarketDataService extends BTERPollingMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTERPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTicker ticker = super.getBTERTicker(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return BTERAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERDepth bterDepth = super.getBTEROrderBook(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return BTERAdapters.adaptOrderBook(bterDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTradeHistory tradeHistory =
        (args != null && args.length > 0 && args[0] != null && args[0] instanceof String) ? super.getBTERTradeHistorySince(currencyPair.baseSymbol, currencyPair.counterSymbol, (String) args[0])
            : super.getBTERTradeHistory(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return BTERAdapters.adaptTrades(tradeHistory, currencyPair);
  }

}
