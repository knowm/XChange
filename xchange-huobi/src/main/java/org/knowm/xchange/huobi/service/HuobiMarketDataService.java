package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.huobi.HuobiAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HuobiMarketDataService extends HuobiMarketDataServiceRaw implements MarketDataService {

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
