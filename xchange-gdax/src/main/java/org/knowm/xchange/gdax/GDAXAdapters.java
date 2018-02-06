package org.knowm.xchange.gdax;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
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
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBook;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductBookEntry;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductStats;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.knowm.xchange.gdax.dto.trade.GDAXFill;
import org.knowm.xchange.gdax.dto.trade.GDAXOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class GDAXAdapters {

  private static Logger logger = LoggerFactory.getLogger(GDAXAdapters.class);

  private GDAXAdapters() {

  }

  protected static Date parseDate(final String rawDate) {

    String modified;
    if (rawDate.length() > 23) {
      modified = rawDate.substring(0, 23);
    } else if (rawDate.endsWith("Z")) {
      switch (rawDate.length()) {
        case 20:
          modified = rawDate.substring(0, 19) + ".000";
          break;
        case 22:
          modified = rawDate.substring(0, 21) + "00";
          break;
        case 23:
          modified = rawDate.substring(0, 22) + "0";
          break;
        default:
          modified = rawDate;
          break;
      }
    } else {
      switch (rawDate.length()) {
        case 19:
          modified = rawDate + ".000";
          break;
        case 21:
          modified = rawDate + "00";
          break;
        case 22:
          modified = rawDate + "0";
          break;
        default:
          modified = rawDate;
          break;
      }
    }
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      return dateFormat.parse(modified);
    } catch (ParseException e) {
      logger.warn("unable to parse rawDate={} modified={}", rawDate, modified, e);
      return null;
    }
  }

  public static Ticker adaptTicker(GDAXProductTicker ticker, GDAXProductStats stats, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getPrice();
    BigDecimal open = stats.getOpen();
    BigDecimal high = stats.getHigh();
    BigDecimal low = stats.getLow();
    BigDecimal buy = ticker.getBid();
    BigDecimal sell = ticker.getAsk();
    BigDecimal volume = ticker.getVolume();
    Date date = parseDate(ticker.getTime());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .open(open)
        .high(high)
        .low(low)
        .bid(buy)
        .ask(sell)
        .volume(volume)
        .timestamp(date)
        .build();
  }

  public static OrderBook adaptOrderBook(GDAXProductBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(GDAXProductBookEntry[] levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>();

    if (levels != null) {
      for (int i = 0; i < levels.length; i++) {
        GDAXProductBookEntry ask = levels[i];

        allLevels.add(new LimitOrder(orderType, ask.getVolume(), currencyPair, "0", null, ask.getPrice()));
      }
    }

    return allLevels;

  }

  public static Wallet adaptAccountInfo(GDAXAccount[] gdaxAccounts) {

    List<Balance> balances = new ArrayList<>(gdaxAccounts.length);

    for (int i = 0; i < gdaxAccounts.length; i++) {

      GDAXAccount gdaxAccount = gdaxAccounts[i];
      balances.add(new Balance(Currency.getInstance(gdaxAccount.getCurrency()), gdaxAccount.getBalance(), gdaxAccount.getAvailable(), gdaxAccount.getHold()));
    }

    return new Wallet(gdaxAccounts[0].getProfile_id(), balances);
  }

  public static OpenOrders adaptOpenOrders(GDAXOrder[] coinbaseExOpenOrders) {
    List<LimitOrder> orders = new ArrayList<>(coinbaseExOpenOrders.length);

    for (int i = 0; i < coinbaseExOpenOrders.length; i++) {
      GDAXOrder order = coinbaseExOpenOrders[i];

      OrderType type = order.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = new CurrencyPair(order.getProductId().replace('-', '/'));

      Date createdAt = parseDate(order.getCreatedAt());

      OrderStatus orderStatus = adaptOrderStatus(order);

      LimitOrder limitOrder = new LimitOrder(type, order.getSize(), currencyPair,
          order.getId(), createdAt, order.getPrice(), order.getPrice(), order.getFilledSize(),order.getFillFees(), orderStatus);

      orders.add(limitOrder);
    }

    return new OpenOrders(orders);
  }

  public static Order adaptOrder(GDAXOrder order) {
    OrderType type = order.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
    CurrencyPair currencyPair = new CurrencyPair(order.getProductId().replace('-', '/'));

    Date createdAt = parseDate(order.getCreatedAt());

    Order returnValue;

    OrderStatus orderStatus = adaptOrderStatus(order);

    BigDecimal averagePrice = order.getExecutedvalue().divide(order.getFilledSize(), new MathContext(8));

    if(order.getType().equals("market")) {
      returnValue = new MarketOrder(
              type,
              order.getSize(),
              currencyPair,
              order.getId(),
              createdAt,
              averagePrice,
              order.getFilledSize(),
              order.getFillFees(),
              orderStatus
              );
    } else if(order.getType().equals("limit")) {
      returnValue = new LimitOrder(
              type,
              order.getSize(),
              currencyPair,
              order.getId(),
              createdAt,
              order.getPrice(),
              averagePrice,
              order.getFilledSize(),
              order.getFillFees(),
              orderStatus);
    } else {
      return null;
    }

    return returnValue;
  }


  public static OrderStatus[] adaptOrderStatuses(GDAXOrder[] orders) {

    OrderStatus[] orderStatuses = new OrderStatus[orders.length];

    Integer i = 0;
    for (GDAXOrder gdaxOrder : orders) {
            orderStatuses[i++] = adaptOrderStatus(gdaxOrder);
    }

    return orderStatuses;

  }

  /** The status from the GDAXOrder object converted to xchange status */
  public static OrderStatus adaptOrderStatus(GDAXOrder order) {
    if(order.getStatus().equals("done")) {

      if(order.getDoneReason().equals("filled"))
        return OrderStatus.FILLED;

      return null;

    }

    if(order.getFilledSize().equals(0.0))
      return OrderStatus.NEW;

    if(order.getFilledSize().compareTo(new BigDecimal(0.0)) > 0
            && order.getSize().compareTo(order.getFilledSize()) < 0)
      return OrderStatus.PARTIALLY_FILLED;

    return null;
  }

  public static UserTrades adaptTradeHistory(GDAXFill[] coinbaseExFills) {

    List<UserTrade> trades = new ArrayList<>(coinbaseExFills.length);

    for (int i = 0; i < coinbaseExFills.length; i++) {
      GDAXFill fill = coinbaseExFills[i];

      OrderType type = fill.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;

      CurrencyPair currencyPair = new CurrencyPair(fill.getProductId().replace('-', '/'));

      UserTrade t = new UserTrade(
          type,
          fill.getSize(),
          currencyPair,
          fill.getPrice(),
          parseDate(fill.getCreatedAt()),
          String.valueOf(fill.getTradeId()),
          fill.getOrderId(),
          fill.getFee(),
          currencyPair.counter
      );
      trades.add(t);
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static Trades adaptTrades(GDAXTrade[] coinbaseExTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(coinbaseExTrades.length);

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

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData, GDAXProduct[] products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
    for (GDAXProduct product : products) {

      BigDecimal minSize = product.getBaseMinSize();
      BigDecimal maxSize = product.getBaseMaxSize();

      CurrencyPair pair = adaptCurrencyPair(product);

      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);
      int priceScale = staticMetaData == null ? 8 : staticMetaData.getPriceScale();
      CurrencyPairMetaData cpmd = new CurrencyPairMetaData(null, minSize, maxSize, priceScale);
      currencyPairs.put(pair, cpmd);

      if (!currencies.containsKey(pair.base))
        currencies.put(pair.base, null);
      if (!currencies.containsKey(pair.counter))
        currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, exchangeMetaData.getPublicRateLimits(), exchangeMetaData.getPrivateRateLimits(), true);
  }

}
