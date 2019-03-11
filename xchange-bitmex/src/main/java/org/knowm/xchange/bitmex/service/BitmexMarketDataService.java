package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Implementation of the market data service for Bitmex
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class BitmexMarketDataService extends BitmexMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexMarketDataService(BitmexExchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String bitmexSymbol = BitmexAdapters.adaptCurrencyPairToSymbol(currencyPair);
    List<BitmexTicker> bitmexTickers = getTicker(bitmexSymbol);

    if (bitmexTickers.isEmpty()) {
      return null;
    }

    BitmexTicker bitmexTicker = bitmexTickers.get(0);
    Ticker ticker =
        new Ticker.Builder()
            .currencyPair(currencyPair)
            .open(bitmexTicker.getOpenValue())
            .last(bitmexTicker.getLastPrice())
            .bid(bitmexTicker.getBidPrice())
            .ask(bitmexTicker.getAskPrice())
            .high(bitmexTicker.getHighPrice())
            .low(bitmexTicker.getLowPrice())
            .vwap(new BigDecimal(bitmexTicker.getVwap()))
            .volume(bitmexTicker.getVolume24h())
            .quoteVolume(null)
            .timestamp(bitmexTicker.getTimestamp())
            .build();

    return ticker;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    String bitmexSymbol = BitmexAdapters.adaptCurrencyPairToSymbol(currencyPair);
    return BitmexAdapters.adaptOrderBook(getBitmexDepth(bitmexSymbol), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer limit = null;
    Long start = null;

    if (args.length > 0) {
      Object arg1 = args[0];
      if (arg1 instanceof Integer) {
        limit = (Integer) arg1;
      } else {
        throw new ExchangeException("args[0] must be of type Integer!");
      }
    }
    if (args.length > 1) {
      Object arg2 = args[1];
      if (arg2 instanceof Long) {
        start = (Long) arg2;
      } else {
        throw new ExchangeException("args[1] must be of type Long!");
      }
    }

    String bitmexSymbol = BitmexAdapters.adaptCurrencyPairToSymbol(currencyPair);
    List<BitmexPublicTrade> trades = getBitmexTrades(bitmexSymbol, limit, start);
    return BitmexAdapters.adaptTrades(trades, currencyPair);
  }
}
