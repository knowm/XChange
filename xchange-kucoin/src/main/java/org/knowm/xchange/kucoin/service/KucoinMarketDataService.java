package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinDealOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bittrex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class KucoinMarketDataService extends KucoinMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KucoinMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return KucoinAdapters.adaptTicker(
        getKucoinTicker(currencyPair),
        currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
    }
    return KucoinAdapters.adaptOrderBook(
        getKucoinOrderBook(currencyPair, limit),
        currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    Long since = null;
    
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer && (Integer) args[0] > 0) {
        limit = (Integer) args[0];
      }
      if (args.length > 1) {
        if (args[1] instanceof Date) {
          since = ((Date) args[1]).getTime();
        }
      }
    }

    KucoinResponse<List<KucoinDealOrder>> response = getKucoinTrades(currencyPair, limit, since);
    List<Trade> trades = response.getData().stream()
        .map(o -> KucoinAdapters.adaptTrade(o, currencyPair))
        .collect(Collectors.toList());
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }
  
  public ExchangeMetaData getMetadata() throws IOException {

    return KucoinAdapters.adaptExchangeMetadata(getKucoinTickers().getData(), getKucoinCurrencies().getData());
  }
}
