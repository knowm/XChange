package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexContract;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.bitmex.BitmexUtils;
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

    List<BitmexTicker> bitmexTickers =
        getTicker(currencyPair.base.toString() + currencyPair.counter.toString());
    if (bitmexTickers.isEmpty()) {
      return null;
    }

    BitmexTicker bitmexTicker = bitmexTickers.get(0);

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    Ticker ticker = null;

    try {
      ticker =
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
              .timestamp(format.parse(bitmexTicker.getTimestamp()))
              .build();
    } catch (ParseException e) {

      return null;
    }

    return ticker;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    BitmexPrompt prompt = null;
    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof BitmexPrompt) {
        prompt = (BitmexPrompt) arg0;
      } else {
        throw new ExchangeException("args[0] must be of type BitmexPrompt!");
      }
    }
    Object[] argsToPass = Arrays.copyOfRange(args, 1, args.length);
    return BitmexAdapters.adaptOrderBook(
        getBitmexDepth(currencyPair, prompt, argsToPass), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BitmexPrompt prompt = null;
    Integer limit = null;
    Long start = null;
    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof BitmexPrompt) {
        prompt = (BitmexPrompt) arg0;
      } else {
        throw new ExchangeException("args[0] must be of type BitmexPrompt!");
      }

      if (args.length > 1) {
        Object arg1 = args[1];
        if (arg1 instanceof Integer) {
          limit = (Integer) arg1;
        } else {
          throw new ExchangeException("args[1] must be of type Integer!");
        }
      }
      if (args.length > 2) {
        Object arg2 = args[2];
        if (arg2 instanceof Long) {
          start = (Long) arg2;
        } else {
          throw new ExchangeException("args[2] must be of type Long!");
        }
      }
    }
    BitmexContract contract = new BitmexContract(currencyPair, prompt);
    String bitmexSymbol = BitmexUtils.translateBitmexContract(contract);
    List<BitmexPublicTrade> trades = getBitmexTrades(bitmexSymbol, limit, start);
    return BitmexAdapters.adaptTrades(trades, currencyPair);
  }
}
