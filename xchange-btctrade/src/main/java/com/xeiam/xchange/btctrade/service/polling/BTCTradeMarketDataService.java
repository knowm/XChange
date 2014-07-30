package com.xeiam.xchange.btctrade.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeAdapters;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class BTCTradeMarketDataService extends BTCTradeMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * @param exchangeSpecification
   */
  public BTCTradeMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) {

    return BTCTradeAdapters.adaptTicker(getBTCTradeTicker(), currencyPair);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) {

    return BTCTradeAdapters.adaptOrderBook(getBTCTradeDepth(), currencyPair);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) {

    final BTCTradeTrade[] trades;
    if (args.length == 0) {
      trades = getBTCTradeTrades();
    }
    else {
      trades = getBTCTradeTrades(toLong(args[0]));
    }
    return BTCTradeAdapters.adaptTrades(trades, currencyPair);
  }

}
