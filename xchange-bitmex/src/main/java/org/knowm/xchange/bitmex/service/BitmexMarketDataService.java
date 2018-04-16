package org.knowm.xchange.bitmex.service;

import static org.knowm.xchange.currency.Currency.USD;

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
import org.knowm.xchange.currency.Currency;
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
  // Bitmex futures contracts
  public static final CurrencyPair XBT_USD = new CurrencyPair(Currency.XBT, USD);
  // Bitmex futures settlement dates
  static Currency H18 = Currency.createCurrency("H18", "March 30th", null);
  public static final CurrencyPair XBT_H18 = new CurrencyPair(Currency.XBT, H18);
  public static final CurrencyPair ADA_H18 = new CurrencyPair(Currency.ADA, H18);
  public static final CurrencyPair BCH_H18 = new CurrencyPair(Currency.BCH, H18);
  public static final CurrencyPair ETH_H18 = new CurrencyPair(Currency.ETH, H18);
  public static final CurrencyPair XRP_H18 = new CurrencyPair(Currency.XRP, H18);
  public static final CurrencyPair LTC_H18 = new CurrencyPair(Currency.LTC, H18);
  public static final CurrencyPair DASH_H18 = new CurrencyPair(Currency.DASH, H18);
  public static final CurrencyPair NEO_H18 = new CurrencyPair(Currency.NEO, H18);
  public static final CurrencyPair XMR_H18 = new CurrencyPair(Currency.XMR, H18);
  public static final CurrencyPair XLM_H18 = new CurrencyPair(Currency.XLM, H18);
  public static final CurrencyPair ZEC_H18 = new CurrencyPair(Currency.ZEC, H18);
  static Currency M18 = Currency.createCurrency("M18", "June 29th", null);
  public static final CurrencyPair XBT_M18 = new CurrencyPair(Currency.XBT, M18);
  public static final CurrencyPair ADA_M18 = new CurrencyPair(Currency.ADA, M18);
  public static final CurrencyPair BCH_M18 = new CurrencyPair(Currency.BCH, M18);
  public static final CurrencyPair ETH_M18 = new CurrencyPair(Currency.ETH, M18);
  public static final CurrencyPair XRP_M18 = new CurrencyPair(Currency.XRP, M18);
  public static final CurrencyPair LTC_M18 = new CurrencyPair(Currency.LTC, M18);
  static Currency U18 = Currency.createCurrency("U18", "September 28th", null);
  public static final CurrencyPair XBT_U18 = new CurrencyPair(Currency.XBT, U18);
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
              .volume(bitmexTicker.getVolume())
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
        getBitmexDepth(BitmexAdapters.adaptCurrencyPair(currencyPair), prompt, argsToPass),
        currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long since = null;
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
    // Trades bitmexTrades = getBitmexTrades(BitmexAdapters.adaptCurrencyPair(currencyPair), prompt,
    // argsToPass);
    return getBitmexTrades(BitmexAdapters.adaptCurrencyPair(currencyPair), prompt, argsToPass);
  }
}
