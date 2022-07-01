package org.knowm.xchange.ccex;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.knowm.xchange.ccex.dto.account.CCEXBalance;
import org.knowm.xchange.ccex.dto.marketdata.CCEXBuySellData;
import org.knowm.xchange.ccex.dto.marketdata.CCEXGetorderbook;
import org.knowm.xchange.ccex.dto.marketdata.CCEXMarket;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrade;
import org.knowm.xchange.ccex.dto.marketdata.CCEXTrades;
import org.knowm.xchange.ccex.dto.ticker.CCEXPriceResponse;
import org.knowm.xchange.ccex.dto.trade.CCEXOpenorder;
import org.knowm.xchange.ccex.dto.trade.CCEXOrderhistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
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
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public class CCEXAdapters {

  private CCEXAdapters() {}

  public static Trades adaptTrades(CCEXTrades cCEXTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    List<CCEXTrade> cCEXTradestmp = cCEXTrades.getResult();

    for (CCEXTrade cCEXTrade : cCEXTradestmp) {
      trades.add(adaptCCEXPublicTrade(cCEXTrade, currencyPair));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptCCEXPublicTrade(CCEXTrade cCEXTrade, CurrencyPair currencyPair) {

    OrderType type =
        cCEXTrade.getOrderType().equalsIgnoreCase("BUY") ? OrderType.BID : OrderType.ASK;
    Date timestamp = stringToDate(cCEXTrade.getTimestamp());

    return new Trade.Builder()
        .type(type)
        .originalAmount(cCEXTrade.getQuantity())
        .currencyPair(currencyPair)
        .price(cCEXTrade.getPrice())
        .timestamp(timestamp)
        .id(cCEXTrade.getId())
        .build();
  }

  /**
   * Adapts a org.knowm.xchange.ccex.api.model.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @return The C-Cex OrderBook
   */
  public static OrderBook adaptOrderBook(
      CCEXGetorderbook ccexOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        createOrders(currencyPair, Order.OrderType.ASK, ccexOrderBook.getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, Order.OrderType.BID, ccexOrderBook.getBids());
    Date date = new Date();
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, List<CCEXBuySellData> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    if (orders == null) {
      return new ArrayList<>();
    }
    for (CCEXBuySellData ask : orders) {
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, CCEXBuySellData priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.getQuantity(), currencyPair, "", null, priceAndAmount.getRate());
  }

  public static CurrencyPair adaptCurrencyPair(CCEXMarket product) {
    return new CurrencyPair(product.getBaseCurrency(), product.getMarketCurrency());
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData exchangeMetaData, List<CCEXMarket> products) {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<CurrencyPair, CurrencyPairMetaData> existingCurrencyPairMetadata =
        exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (CCEXMarket product : products) {
      BigDecimal minSize = product.getMinTradeSize();
      CurrencyPair pair = adaptCurrencyPair(product);
      CurrencyPairMetaData existingMetaForPair = existingCurrencyPairMetadata.get(pair);
      FeeTier[] existingFeeTiers = null;
      if (existingMetaForPair != null) {
        existingFeeTiers = existingMetaForPair.getFeeTiers();
      }
      CurrencyPairMetaData cpmd =
          new CurrencyPairMetaData(null, minSize, null, 0, existingFeeTiers);
      currencyPairs.put(pair, cpmd);
      currencies.put(pair.base, null);
      currencies.put(pair.counter, null);
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("-");
    return new CurrencyPair(currencies[0].toUpperCase(), currencies[1].toUpperCase());
  }

  public static Date stringToDate(String dateString) {

    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      return sdf.parse(dateString);
    } catch (ParseException e) {
      return new Date(0);
    }
  }

  public static Wallet adaptWallet(List<CCEXBalance> balances) {

    List<Balance> wallets = new ArrayList<>(balances.size());

    for (CCEXBalance balance : balances) {
      wallets.add(
          new Balance(
              Currency.getInstance(balance.getCurrency().toUpperCase()),
              balance.getBalance(),
              balance.getAvailable(),
              balance.getBalance().subtract(balance.getAvailable()).subtract(balance.getPending()),
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              balance.getPending()));
    }

    return Wallet.Builder.from(wallets).build();
  }

  public static List<LimitOrder> adaptOpenOrders(List<CCEXOpenorder> cCexOpenOrders) {

    List<LimitOrder> openOrders = new ArrayList<>();

    for (CCEXOpenorder order : cCexOpenOrders) {
      openOrders.add(adaptOpenOrder(order));
    }

    return openOrders;
  }

  public static LimitOrder adaptOpenOrder(CCEXOpenorder cCEXOpenOrder) {

    OrderType type =
        cCEXOpenOrder.getOrderType().equalsIgnoreCase("LIMIT_SELL") ? OrderType.ASK : OrderType.BID;
    String[] currencies = cCEXOpenOrder.getExchange().split("-");
    CurrencyPair pair = new CurrencyPair(currencies[1], currencies[0]);

    return new LimitOrder(
        type,
        cCEXOpenOrder.getQuantityRemaining(),
        pair,
        cCEXOpenOrder.getOrderUuid(),
        null,
        cCEXOpenOrder.getLimit());
  }

  public static List<UserTrade> adaptUserTrades(List<CCEXOrderhistory> cCEXOrderhistory) {

    List<UserTrade> trades = new ArrayList<>();

    for (CCEXOrderhistory cCEXTrade : cCEXOrderhistory) {
      trades.add(adaptUserTrade(cCEXTrade));
    }
    return trades;
  }

  public static UserTrade adaptUserTrade(CCEXOrderhistory trade) {

    String[] currencies = trade.getExchange().split("-");
    CurrencyPair currencyPair = new CurrencyPair(currencies[1], currencies[0]);

    OrderType orderType =
        trade.getOrderType().equalsIgnoreCase("LIMIT_BUY") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity().subtract(trade.getQuantityRemaining());
    Date date = CCEXUtils.toDate(trade.getTimeStamp());
    String orderId = String.valueOf(trade.getOrderUuid());

    BigDecimal price = trade.getPricePerUnit();

    if (price == null) {
      price = trade.getLimit();
    }

    return new UserTrade.Builder()
        .type(orderType)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(orderId)
        .orderId(orderId)
        .feeAmount(trade.getCommission())
        .feeCurrency(currencyPair.counter)
        .build();
  }

  public static Ticker adaptTicker(CCEXPriceResponse cCEXTicker, CurrencyPair currencyPair) {

    BigDecimal last = cCEXTicker.getLastbuy();
    BigDecimal bid = cCEXTicker.getBuy();
    BigDecimal ask = cCEXTicker.getSell();
    BigDecimal high = cCEXTicker.getHigh();
    BigDecimal low = cCEXTicker.getLow();
    BigDecimal volume = cCEXTicker.getBuysupport();

    Date timestamp = new Date(cCEXTicker.getUpdated());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }
}
