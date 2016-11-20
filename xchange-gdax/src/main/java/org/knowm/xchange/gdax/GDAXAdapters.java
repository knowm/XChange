package org.knowm.xchange.gdax;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBookEntry;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;

public class GDAXAdapters {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private GDAXAdapters() {

  }

  private static Date parseDate(String rawDate) {

    //    System.out.println("before: " + rawDate);
    try {
      if (rawDate.length() == 20 && rawDate.endsWith("Z")) {
        rawDate = rawDate.substring(0, 19) + ".000Z";
      } else if (rawDate.length() == 21) {
        rawDate = rawDate.substring(0, 20) + "000";
      } else if (rawDate.length() == 22) {
        rawDate = rawDate.substring(0, 21) + "00";
      } else if (rawDate.length() == 23) {
        rawDate = rawDate.substring(0, 22) + "0";
      } else {
        rawDate = rawDate.substring(0, rawDate.length() < 23 ? rawDate.length() : 23);
      }
      //      System.out.println("after: " + rawDate);
      //      System.out.println("");

      return dateFormat.parse(rawDate);
    } catch (ParseException e) {
      System.err.println("rawDate: " + rawDate);
      e.printStackTrace();
      return null;
    }
  }

  public static Ticker adaptTicker(GDAXProductTicker ticker, GDAXProductStats stats, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getPrice();
    BigDecimal high = stats.getHigh();
    BigDecimal low = stats.getLow();
    BigDecimal buy = ticker.getBid();
    BigDecimal sell = ticker.getAsk();
    BigDecimal volume = ticker.getVolume();
    Date date = parseDate(ticker.getTime());

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).timestamp(date).build();
  }

  public static OrderBook adaptOrderBook(GDAXProductBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(GDAXProductBookEntry[] levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<LimitOrder>(levels.length);
    for (int i = 0; i < levels.length; i++) {
      GDAXProductBookEntry ask = levels[i];

      allLevels.add(new LimitOrder(orderType, ask.getVolume(), currencyPair, "0", null, ask.getPrice()));
    }

    return allLevels;

  }

  public static Wallet adaptAccountInfo(GDAXAccount[] coinbaseExAccountInfo) {
    List<Balance> balances = new ArrayList<Balance>(coinbaseExAccountInfo.length);

    for (int i = 0; i < coinbaseExAccountInfo.length; i++) {
      GDAXAccount account = coinbaseExAccountInfo[i];

      balances.add(new Balance(Currency.getInstance(account.getCurrency()), account.getBalance(), account.getAvailable(), account.getHold()));
    }

    return new Wallet(coinbaseExAccountInfo[0].getProfile_id(), balances);
  }

  public static OpenOrders adaptOpenOrders(GDAXOrder[] coinbaseExOpenOrders) {
    List<LimitOrder> orders = new ArrayList<LimitOrder>(coinbaseExOpenOrders.length);

    for (int i = 0; i < coinbaseExOpenOrders.length; i++) {
      GDAXOrder order = coinbaseExOpenOrders[i];

      OrderType type = order.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = new CurrencyPair(order.getProductId().replace("-", "/"));

      Date createdAt = parseDate(order.getCreatedAt());

      orders.add(new LimitOrder(type, order.getSize(), currencyPair, order.getId(), createdAt, order.getPrice()));

    }

    return new OpenOrders(orders);
  }

  public static UserTrades adaptTradeHistory(GDAXFill[] coinbaseExFills) {
    List<UserTrade> trades = new ArrayList<UserTrade>(coinbaseExFills.length);

    for (int i = 0; i < coinbaseExFills.length; i++) {
      GDAXFill fill = coinbaseExFills[i];

      // yes, sell means buy for Coinbase reported trades..
      OrderType type = fill.getSide().equals("sell") ? OrderType.BID : OrderType.ASK;

      CurrencyPair currencyPair = new CurrencyPair(fill.getProductId().replace("-", "/"));

      // ToDo add fee amount
      UserTrade t = new UserTrade(type, fill.getSize(), currencyPair, fill.getPrice(), parseDate(fill.getCreatedAt()),
          String.valueOf(fill.getTradeId()), fill.getOrderId(), null, (Currency) null);
      trades.add(t);
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static Trades adaptTrades(GDAXTrade[] coinbaseExTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>(coinbaseExTrades.length);

    for (int i = 0; i < coinbaseExTrades.length; i++) {
      GDAXTrade trade = coinbaseExTrades[i];

      // yes, sell means buy for gdax reported trades..
      OrderType type = trade.getSide().equals("sell") ? OrderType.BID : OrderType.ASK;

      Trade t = new Trade(type, trade.getSize(), currencyPair, trade.getPrice(), parseDate(trade.getTimestamp()), String.valueOf(trade.getTradeId()));
      trades.add(t);
    }

    return new Trades(trades, coinbaseExTrades[0].getTradeId(), TradeSortType.SortByID);
  }

  public static CurrencyPair adaptCurrencyPair(GDAXProduct product) {
    return new CurrencyPair(product.getBaseCurrency(), product.getTargetCurrency());
  }

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData, List<GDAXProduct> products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    for (GDAXProduct product : products) {
      BigDecimal minSize = product.getBaseMinSize().setScale(product.getQuoteIncrement().scale(), BigDecimal.ROUND_UNNECESSARY);
      BigDecimal maxSize = product.getBaseMaxSize().setScale(product.getQuoteIncrement().scale(), BigDecimal.ROUND_UNNECESSARY);
      CurrencyPairMetaData cpmd = new CurrencyPairMetaData(null, minSize, maxSize, 8); // TODO 8 is a wild guess
      CurrencyPair pair = adaptCurrencyPair(product);
      currencyPairs.put(pair, cpmd);
      currencies.put(pair.base, null);
      currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, exchangeMetaData.getPublicRateLimits(), exchangeMetaData.getPrivateRateLimits(), true);
  }

}
