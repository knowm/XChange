package org.knowm.xchange.coinbaseex;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.knowm.xchange.coinbaseex.dto.account.CoinbaseExAccount;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProduct;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBookEntry;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import org.knowm.xchange.coinbaseex.dto.marketdata.CoinbaseExTrade;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExFill;
import org.knowm.xchange.coinbaseex.dto.trade.CoinbaseExOrder;
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
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class CoinbaseExAdapters {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private CoinbaseExAdapters() {

  }

  private static Date parseDate(String rawDate) {
    try {
      return dateFormat.parse(rawDate.substring(0, 23));
    } catch (ParseException e) {
      return null;
    }
  }

  public static Ticker adaptTicker(CoinbaseExProductTicker ticker, CoinbaseExProductStats stats, CoinbaseExProductBook book,
      CurrencyPair currencyPair) {

    BigDecimal last = ticker != null ? ticker.getPrice() : null;
    BigDecimal high = stats != null ? stats.getHigh() : null;
    BigDecimal low = stats != null ? stats.getLow() : null;
    BigDecimal buy = book != null ? book.getBestBid().getPrice() : null;
    BigDecimal sell = book != null ? book.getBestAsk().getPrice() : null;
    BigDecimal volume = stats != null ? stats.getVolume() : null;
    Date date = ticker != null ? ticker.getTime() : new Date();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(buy).ask(sell).volume(volume).timestamp(date).build();
  }

  public static OrderBook adaptOrderBook(CoinbaseExProductBook book, CurrencyPair currencyPair) {
    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(CoinbaseExProductBookEntry[] levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<LimitOrder>(levels.length);
    for (int i = 0; i < levels.length; i++) {
      CoinbaseExProductBookEntry ask = levels[i];

      allLevels.add(new LimitOrder(orderType, ask.getVolume(), currencyPair, "0", null, ask.getPrice()));
    }

    return allLevels;

  }

  public static Wallet adaptAccountInfo(CoinbaseExAccount[] coinbaseExAccountInfo) {
    List<Balance> balances = new ArrayList<Balance>(coinbaseExAccountInfo.length);

    for (int i = 0; i < coinbaseExAccountInfo.length; i++) {
      CoinbaseExAccount account = coinbaseExAccountInfo[i];

      balances.add(new Balance(Currency.getInstance(account.getCurrency()), account.getBalance(), account.getAvailable(), account.getHold()));
    }

    return new Wallet(coinbaseExAccountInfo[0].getProfile_id(), balances);
  }

  public static OpenOrders adaptOpenOrders(CoinbaseExOrder[] coinbaseExOpenOrders) {
    List<LimitOrder> orders = new ArrayList<LimitOrder>(coinbaseExOpenOrders.length);

    for (int i = 0; i < coinbaseExOpenOrders.length; i++) {
      CoinbaseExOrder order = coinbaseExOpenOrders[i];

      OrderType type = order.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = new CurrencyPair(order.getProductId().replace("-", "/"));

      Date createdAt = parseDate(order.getCreatedAt());

      orders.add(new LimitOrder(type, order.getSize(), currencyPair, order.getId(), createdAt, order.getPrice()));

    }

    return new OpenOrders(orders);
  }

  public static UserTrades adaptTradeHistory(CoinbaseExFill[] coinbaseExFills) {
    List<UserTrade> trades = new ArrayList<UserTrade>(coinbaseExFills.length);

    for (int i = 0; i < coinbaseExFills.length; i++) {
      CoinbaseExFill fill = coinbaseExFills[i];

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

  public static Trades adaptTrades(CoinbaseExTrade[] coinbaseExTrades, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<Trade>(coinbaseExTrades.length);

    for (int i = 0; i < coinbaseExTrades.length; i++) {
      CoinbaseExTrade trade = coinbaseExTrades[i];

      // yes, sell means buy for Coinbase reported trades..
      OrderType type = trade.getSide().equals("sell") ? OrderType.BID : OrderType.ASK;

      Trade t = new Trade(type, trade.getSize(), currencyPair, trade.getPrice(), parseDate(trade.getTimestamp()), String.valueOf(trade.getTradeId()));
      trades.add(t);
    }

    return new Trades(trades, TradeSortType.SortByID);
  }

  public static List<CurrencyPair> adaptProductsToSupportedExchangeSymbols(List<CoinbaseExProduct> products) {
    List<CurrencyPair> result = new ArrayList<CurrencyPair>();
    for (CoinbaseExProduct product : products) {
      result.add(new CurrencyPair(product.getBaseCurrency(), product.getTargetCurrency()));
    }

    return result;
  }
}
