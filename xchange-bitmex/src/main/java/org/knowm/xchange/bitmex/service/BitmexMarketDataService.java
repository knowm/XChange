package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the market data service for Bitmex
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitmexMarketDataService extends BitmexMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    List<BitmexTicker> bitmexTickers = getTicker(currencyPair.base.toString() + currencyPair.counter.toString());
    BitmexTicker bitmexTicker = bitmexTickers.get(0);

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    Ticker ticker = null;

    try {
      ticker = new Ticker.Builder().currencyPair(currencyPair).open(bitmexTicker.getOpenValue())
              .last(bitmexTicker.getLastPrice()).bid(bitmexTicker.getBidPrice()).ask(bitmexTicker.getAskPrice())
              .high(bitmexTicker.getHighPrice()).low(bitmexTicker.getLowPrice())
              .vwap(new BigDecimal(bitmexTicker.getVwap())).volume(bitmexTicker.getVolume()).quoteVolume(null)
              .timestamp(format.parse(bitmexTicker.getTimestamp())).build();
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
      }
      else {
        throw new ExchangeException("args[0] must be of type BitmexPrompt!");
      }
    }
    Object[] argsToPass = Arrays.copyOfRange(args, 1, args.length);
    return BitmexAdapters.adaptOrderBook(getBitmexDepth(BitmexAdapters.adaptCurrencyPair(currencyPair), prompt, argsToPass), currencyPair);

  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long since = null;
    BitmexPrompt prompt = null;
    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof BitmexPrompt) {
        prompt = (BitmexPrompt) arg0;
      }
      else {
        throw new ExchangeException("args[0] must be of type BitmexPrompt!");
      }
    }
    Object[] argsToPass = Arrays.copyOfRange(args, 1, args.length);
    // Trades bitmexTrades = getBitmexTrades(BitmexAdapters.adaptCurrencyPair(currencyPair), prompt, argsToPass);
    return getBitmexTrades(BitmexAdapters.adaptCurrencyPair(currencyPair), prompt, argsToPass);

  }
}
