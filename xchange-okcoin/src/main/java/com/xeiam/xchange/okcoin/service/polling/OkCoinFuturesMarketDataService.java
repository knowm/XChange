package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.currency.FuturesPrompt;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class OkCoinFuturesMarketDataService extends OkCoinMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchangeSpecification
   */
  public OkCoinFuturesMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkCoinAdapters.adaptTicker(getFuturesTicker(currencyPair, (FuturesPrompt) args[0]), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkCoinAdapters.adaptOrderBook(getFuturesDepth(currencyPair, (FuturesPrompt) args[0]), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {    
    return OkCoinAdapters.adaptTrades(getFuturesTrades(currencyPair, (FuturesPrompt) args[0]), currencyPair);
  }
}
