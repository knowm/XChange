package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.huobi.HuobiAdapters;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class HuobiMarketDataService extends HuobiMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return HuobiAdapters.adaptTicker(getBitVcTicker(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return HuobiAdapters.adaptOrderBook(getBitVcDepth(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return HuobiAdapters.adaptTrades(getBitVcDetail(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

}
