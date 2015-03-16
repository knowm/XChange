package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BTCTradeMarketDataService extends BTCTradeMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeMarketDataService(Exchange exchange) {

    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCTradeAdapters.adaptTicker(getBTCTradeTicker(), currencyPair);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCTradeAdapters.adaptOrderBook(getBTCTradeDepth(), currencyPair);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    final BTCTradeTrade[] trades;
    if (args.length == 0) {
      trades = getBTCTradeTrades();
    } else {
      trades = getBTCTradeTrades(toLong(args[0]));
    }
    return BTCTradeAdapters.adaptTrades(trades, currencyPair);
  }

}
