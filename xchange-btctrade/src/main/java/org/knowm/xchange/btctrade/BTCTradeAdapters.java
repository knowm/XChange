package org.knowm.xchange.btctrade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.knowm.xchange.btctrade.dto.BTCTradeResult;
import org.knowm.xchange.btctrade.dto.account.BTCTradeBalance;
import org.knowm.xchange.btctrade.dto.account.BTCTradeWallet;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeDepth;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import org.knowm.xchange.btctrade.dto.trade.BTCTradeOrder;
import org.knowm.xchange.btctrade.dto.trade.BTCTradePlaceOrderResult;
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
import org.knowm.xchange.exceptions.ExchangeException;

/** Various adapters for converting from BTCTrade DTOs to XChange DTOs. */
public final class BTCTradeAdapters {

  private static final Map<String, CurrencyPair> currencyPairs = getCurrencyPairs();

  /** private Constructor */
  private BTCTradeAdapters() {}

  private static Map<String, CurrencyPair> getCurrencyPairs() {

    Map<String, CurrencyPair> currencyPairs = new HashMap<>(4);
    currencyPairs.put("1", CurrencyPair.BTC_CNY);
    // Seems they only provides API methods for the BTC_CNY.
    // But, anyway, we can place LTC_CNY orders from the website,
    // and then we may got the open orders by API method.
    currencyPairs.put("2", CurrencyPair.LTC_CNY);
    // 3 -> CurrencyPair.DOGE_CNY?
    // 4 -> CurrencyPair.YBC_CNY?
    return currencyPairs;
  }

  public static Date adaptDatetime(String datetime) {

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
      return sdf.parse(datetime);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static CurrencyPair adaptCurrencyPair(String coin) {

    return currencyPairs.get(coin);
  }

  public static Ticker adaptTicker(BTCTradeTicker btcTradeTicker, CurrencyPair currencyPair) {

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .high(btcTradeTicker.getHigh())
        .low(btcTradeTicker.getLow())
        .bid(btcTradeTicker.getBuy())
        .ask(btcTradeTicker.getSell())
        .last(btcTradeTicker.getLast())
        .volume(btcTradeTicker.getVol())
        .build();
  }

  public static OrderBook adaptOrderBook(BTCTradeDepth btcTradeDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptLimitOrders(btcTradeDepth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = adaptLimitOrders(btcTradeDepth.getBids(), currencyPair, OrderType.BID);
    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptLimitOrders(
      BigDecimal[][] orders, CurrencyPair currencyPair, OrderType type) {

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);
    for (BigDecimal[] order : orders) {
      limitOrders.add(adaptLimitOrder(order, currencyPair, type));
    }
    return limitOrders;
  }

  private static LimitOrder adaptLimitOrder(
      BigDecimal[] order, CurrencyPair currencyPair, OrderType type) {

    return new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
  }

  public static Trades adaptTrades(BTCTradeTrade[] btcTradeTrades, CurrencyPair currencyPair) {

    int length = btcTradeTrades.length;
    List<Trade> trades = new ArrayList<>(length);
    for (BTCTradeTrade btcTradeTrade : btcTradeTrades) {
      trades.add(adaptTrade(btcTradeTrade, currencyPair));
    }
    long lastID = length > 0 ? btcTradeTrades[length - 1].getTid() : 0L;
    return new Trades(trades, lastID, TradeSortType.SortByID);
  }

  private static Trade adaptTrade(BTCTradeTrade btcTradeTrade, CurrencyPair currencyPair) {

    return new Trade.Builder()
        .type(adaptOrderType(btcTradeTrade.getType()))
        .originalAmount(btcTradeTrade.getAmount())
        .currencyPair(currencyPair)
        .price(btcTradeTrade.getPrice())
        .timestamp(new Date(btcTradeTrade.getDate() * 1000))
        .id(String.valueOf(btcTradeTrade.getTid()))
        .build();
  }

  private static OrderType adaptOrderType(String type) {

    return type.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  private static void checkException(BTCTradeResult result) {

    if (!result.isSuccess()) {
      throw new ExchangeException(result.getMessage());
    }
  }

  public static boolean adaptResult(BTCTradeResult result) {

    checkException(result);

    return true;
  }

  public static Wallet adaptWallet(BTCTradeBalance balance) {

    checkException(balance);

    List<Balance> balances = new ArrayList<>(5);
    balances.add(
        new Balance(
            Currency.BTC,
            nullSafeSum(balance.getBtcBalance(), balance.getBtcReserved()),
            zeroIfNull(balance.getBtcBalance()),
            zeroIfNull(balance.getBtcReserved())));
    balances.add(
        new Balance(
            Currency.LTC,
            nullSafeSum(balance.getLtcBalance(), balance.getLtcReserved()),
            zeroIfNull(balance.getLtcBalance()),
            zeroIfNull(balance.getLtcReserved())));
    balances.add(
        new Balance(
            Currency.DOGE,
            nullSafeSum(balance.getDogeBalance(), balance.getDogeReserved()),
            zeroIfNull(balance.getDogeBalance()),
            zeroIfNull(balance.getDogeReserved())));
    balances.add(
        new Balance(
            Currency.YBC,
            nullSafeSum(balance.getYbcBalance(), balance.getYbcReserved()),
            zeroIfNull(balance.getYbcBalance()),
            zeroIfNull(balance.getYbcReserved())));
    balances.add(
        new Balance(
            Currency.CNY,
            nullSafeSum(balance.getCnyBalance(), balance.getCnyReserved()),
            zeroIfNull(balance.getCnyBalance()),
            zeroIfNull(balance.getCnyReserved())));
    return Wallet.Builder.from(balances).build();
  }

  static BigDecimal nullSafeSum(BigDecimal a, BigDecimal b) {
    return zeroIfNull(a).add(zeroIfNull(b));
  }

  static BigDecimal zeroIfNull(BigDecimal a) {
    return a == null ? BigDecimal.ZERO : a;
  }

  public static String adaptDepositAddress(BTCTradeWallet wallet) {

    checkException(wallet);

    return wallet.getAddress();
  }

  public static String adaptPlaceOrderResult(BTCTradePlaceOrderResult result) {

    checkException(result);

    return result.getId();
  }

  public static OpenOrders adaptOpenOrders(BTCTradeOrder[] btcTradeOrders) {

    List<LimitOrder> openOrders = new ArrayList<>(btcTradeOrders.length);
    for (BTCTradeOrder order : btcTradeOrders) {
      LimitOrder limitOrder = adaptLimitOrder(order);
      if (limitOrder != null) {
        openOrders.add(limitOrder);
      }
    }
    return new OpenOrders(openOrders);
  }

  private static LimitOrder adaptLimitOrder(BTCTradeOrder order) {

    CurrencyPair currencyPair = adaptCurrencyPair(order.getCoin());

    final LimitOrder limitOrder;
    if (currencyPair == null) {
      // Unknown currency pair
      limitOrder = null;
    } else {
      limitOrder =
          new LimitOrder(
              adaptOrderType(order.getType()),
              order.getAmountOutstanding(),
              currencyPair,
              order.getId(),
              adaptDatetime(order.getDatetime()),
              order.getPrice());
    }

    return limitOrder;
  }

  public static UserTrades adaptTrades(
      BTCTradeOrder[] btcTradeOrders, BTCTradeOrder[] btcTradeOrderDetails) {

    List<UserTrade> trades = new ArrayList<>();
    for (int i = 0; i < btcTradeOrders.length; i++) {
      BTCTradeOrder order = btcTradeOrders[i];

      CurrencyPair currencyPair = adaptCurrencyPair(order.getCoin());

      if (currencyPair != null) {
        BTCTradeOrder orderDetail = btcTradeOrderDetails[i];

        for (org.knowm.xchange.btctrade.dto.trade.BTCTradeTrade trade : orderDetail.getTrades()) {
          trades.add(adaptTrade(order, trade, currencyPair));
        }
      }
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptTrade(
      BTCTradeOrder order,
      org.knowm.xchange.btctrade.dto.trade.BTCTradeTrade trade,
      CurrencyPair currencyPair) {

    return new UserTrade.Builder()
        .type(adaptOrderType(order.getType()))
        .originalAmount(trade.getAmount())
        .currencyPair(currencyPair)
        .price(trade.getPrice())
        .timestamp(adaptDatetime(trade.getDatetime()))
        .id(trade.getTradeId())
        .orderId(order.getId())
        .build();
  }
}
