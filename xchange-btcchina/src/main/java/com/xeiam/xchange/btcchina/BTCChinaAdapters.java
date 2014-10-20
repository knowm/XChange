package com.xeiam.xchange.btcchina;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.BTCChinaValue;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaMarketDepth;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaMarketDepthOrder;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrderStatus;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrders;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from BTCChina DTOs to XChange DTOs.
 */
public final class BTCChinaAdapters {

  private static final int TICKER_MARKET_KEY_PREFIX_LENGTH = "ticker_".length();
  private static final int ORDERS_MARKET_KEY_PREFIX_LENGTH = "order_".length();

  /**
   * private Constructor
   */
  private BTCChinaAdapters() {

  }

  /**
   * Adapts an array of btcchinaOrders to a List of LimitOrders
   */
  public static List<LimitOrder> adaptOrders(BigDecimal[][] btcchinaOrders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(btcchinaOrders.length);

    for (BigDecimal[] btcchinaOrder : btcchinaOrders) {
      limitOrders.add(adaptOrder(btcchinaOrder[1], btcchinaOrder[0], currencyPair, orderType));
    }

    return limitOrders;
  }

  /**
   * Adapts a List of btcchinaOrders to a List of LimitOrders
   * 
   * @deprecated Use {@link #adaptOrders(BigDecimal[][], CurrencyPair, OrderType)} instead.
   */
  @Deprecated
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> btcchinaOrders, CurrencyPair currencyPair, OrderType orderType) {

    return adaptOrders(btcchinaOrders.toArray(new BigDecimal[0][0]), currencyPair, orderType);
  }

  /**
   * Adapts a BTCChinaOrder to a LimitOrder
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, amount, currencyPair, "", null, price);

  }

  /**
   * Adapts a BTCChinaTrade to a Trade Object
   * 
   * @param btcChinaTrade A BTCChina trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(BTCChinaTrade btcChinaTrade, CurrencyPair currencyPair) {

    BigDecimal amount = btcChinaTrade.getAmount();
    BigDecimal price = btcChinaTrade.getPrice();
    Date date = adaptDate(btcChinaTrade.getDate());
    OrderType orderType = btcChinaTrade.getOrderType().equals("sell") ? OrderType.ASK : OrderType.BID;

    final String tradeId = String.valueOf(btcChinaTrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(List<BTCChinaTrade> btcchinaTrades, CurrencyPair currencyPair) {

    return adaptTrades(btcchinaTrades.toArray(new BTCChinaTrade[0]), currencyPair);
  }

  /**
   * Adapts a BTCChinaTrade[] to a Trades Object.
   * 
   * @param btcchinaTrades The BTCChina trade data
   * @return The trades
   */
  public static Trades adaptTrades(BTCChinaTrade[] btcchinaTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(btcchinaTrades.length);
    long latestTradeId = 0;
    for (BTCChinaTrade btcchinaTrade : btcchinaTrades) {
      long tradeId = btcchinaTrade.getTid();
      if (tradeId > latestTradeId)
        latestTradeId = tradeId;
      tradesList.add(adaptTrade(btcchinaTrade, currencyPair));
    }
    return new Trades(tradesList, latestTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a BTCChinaTicker to a Ticker Object
   */
  public static Ticker adaptTicker(BTCChinaTicker btcChinaTicker, CurrencyPair currencyPair) {

    return adaptTicker(btcChinaTicker.getTicker(), currencyPair);
  }

  public static Ticker adaptTicker(BTCChinaTickerObject ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getLast();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal buy = ticker.getBuy();
    BigDecimal sell = ticker.getSell();
    BigDecimal volume = ticker.getVol();
    Date date = adaptDate(ticker.getDate());

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).withTimestamp(date).build();
  }

  public static Map<CurrencyPair, Ticker> adaptTickers(BTCChinaTicker btcChinaTicker) {

    Map<CurrencyPair, Ticker> tickers = new LinkedHashMap<CurrencyPair, Ticker>(btcChinaTicker.size());
    for (Map.Entry<String, BTCChinaTickerObject> entry : btcChinaTicker.entrySet()) {
      CurrencyPair currencyPair = adaptCurrencyPairFromTickerMarketKey(entry.getKey());
      tickers.put(currencyPair, adaptTicker(entry.getValue(), currencyPair));
    }
    return tickers;
  }

  /**
   * Adapts a BTCChinaAccountInfoResponse to AccountInfo Object
   */
  public static AccountInfo adaptAccountInfo(BTCChinaResponse<BTCChinaAccountInfo> response) {

    BTCChinaAccountInfo result = response.getResult();
    return new AccountInfo(result.getProfile().getUsername(), result.getProfile().getTradeFee(), BTCChinaAdapters.adaptWallets(result.getBalances(), result.getFrozens()));
  }

  public static List<Wallet> adaptWallets(Map<String, BTCChinaValue> balances, Map<String, BTCChinaValue> frozens) {

    List<Wallet> wallets = new ArrayList<Wallet>(balances.size());

    for (Map.Entry<String, BTCChinaValue> entry : balances.entrySet()) {
      Wallet wallet;
      BTCChinaValue frozen = frozens.get(entry.getKey());
      if (frozen != null) {
        wallet = adaptWallet(entry.getValue(), frozen);
        if (wallet != null) {
          wallets.add(wallet);
        }
      }
    }
    return wallets;

  }

  /**
   * Adapts BTCChinaValue balance, BTCChinaValue frozen to wallet
   */
  public static Wallet adaptWallet(BTCChinaValue balance, BTCChinaValue frozen) {

    if (balance != null && frozen != null) {
      BigDecimal balanceAmount = BTCChinaUtils.valueToBigDecimal(balance);
      BigDecimal frozenAmount = BTCChinaUtils.valueToBigDecimal(frozen);
      BigDecimal cash = balanceAmount.add(frozenAmount);
      return new Wallet(balance.getCurrency(), cash);
    }
    else {
      return null;
    }
  }

  /**
   * Adapts {@link BTCChinaDepth} to {@link OrderBook}.
   * 
   * @param btcChinaDepth {@link BTCChinaDepth}
   * @param currencyPair the currency pair of the depth.
   * @return {@link OrderBook}
   */
  public static OrderBook adaptOrderBook(BTCChinaDepth btcChinaDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = BTCChinaAdapters.adaptOrders(btcChinaDepth.getAsksArray(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = BTCChinaAdapters.adaptOrders(btcChinaDepth.getBidsArray(), currencyPair, OrderType.BID);

    return new OrderBook(BTCChinaAdapters.adaptDate(btcChinaDepth.getDate()), asks, bids);
  }

  public static OrderBook adaptOrderBook(BTCChinaMarketDepth marketDepth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptLimitOrders(marketDepth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptLimitOrders(marketDepth.getBids(), OrderType.BID, currencyPair);
    return new OrderBook(adaptDate(marketDepth.getDate()), asks, bids);
  }

  public static List<LimitOrder> adaptLimitOrders(BTCChinaMarketDepthOrder[] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);
    for (BTCChinaMarketDepthOrder order : orders) {
      limitOrders.add(adaptLimitOrder(order, orderType, currencyPair));
    }
    return limitOrders;
  }

  public static LimitOrder adaptLimitOrder(BTCChinaMarketDepthOrder order, OrderType orderType, CurrencyPair currencyPair) {

    return new LimitOrder.Builder(orderType, currencyPair).setLimitPrice(order.getPrice()).setTradableAmount(order.getAmount()).build();
  }

  /**
   * Adapts List&lt;BTCChinaOrder&gt; to OpenOrders.
   *
   * @deprecated Do not use this anymore.
   */
  @Deprecated
  public static OpenOrders adaptOpenOrders(List<BTCChinaOrder> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders == null ? 0 : orders.size());

    if (orders != null) {
      for (BTCChinaOrder order : orders) {
        if (order.getStatus().equals("open")) {
          LimitOrder limitOrder = adaptLimitOrder(order);
          if (limitOrder != null) {
            limitOrders.add(limitOrder);
          }
        }
      }
    }

    return new OpenOrders(limitOrders);
  }

  public static List<LimitOrder> adaptOrders(BTCChinaOrder[] orders, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);

    for (BTCChinaOrder order : orders) {
      LimitOrder limitOrder = adaptLimitOrder(order, currencyPair);
      limitOrders.add(limitOrder);
    }

    return limitOrders;
  }

  /**
   * @deprecated Use {@link #adaptOrders(BTCChinaOrder[], CurrencyPair)} instead.
   */
  @Deprecated
  public static List<LimitOrder> adaptOrders(List<BTCChinaOrder> orders, CurrencyPair currencyPair) {

    return adaptOrders(orders.toArray(new BTCChinaOrder[0]), currencyPair);
  }

  public static List<LimitOrder> adaptOrders(BTCChinaOrders orders, CurrencyPair specifiedCurrencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    BTCChinaOrder[] certainCurrencyPairOrders = orders.getOrdersArray();
    if (certainCurrencyPairOrders != null) {
      limitOrders.addAll(adaptOrders(certainCurrencyPairOrders, specifiedCurrencyPair));
    }

    for (Map.Entry<String, BTCChinaOrder[]> entry : orders.entrySet()) {
      CurrencyPair currencyPair = adaptCurrencyPairFromOrdersMarketKey(entry.getKey());
      limitOrders.addAll(adaptOrders(entry.getValue(), currencyPair));
    }

    return limitOrders;
  }

  /**
   * Adapts BTCChinaOrder to LimitOrder.
   * 
   * @deprecated Use {@link #adaptLimitOrder(BTCChinaOrder, CurrencyPair)} instead.
   */
  @Deprecated
  public static LimitOrder adaptLimitOrder(BTCChinaOrder order) {

    return adaptLimitOrder(order, CurrencyPair.BTC_CNY);
  }

  /**
   * Adapts BTCChinaOrder to LimitOrder.
   */
  public static LimitOrder adaptLimitOrder(BTCChinaOrder order, CurrencyPair currencyPair) {

    OrderType orderType = order.getType().equals("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = order.getAmount();
    String id = Long.toString(order.getId());
    Date date = adaptDate(order.getDate());
    BigDecimal price = order.getPrice();

    return new LimitOrder(orderType, amount, currencyPair, id, date, price);
  }

  public static Trade adaptTransaction(final BTCChinaTransaction transaction) {

    final String type = transaction.getType();

    // could also be 'rebate' or other
    if (!(type.startsWith("buy") || type.startsWith("sell"))) {
      return null;
    }

    final OrderType orderType = type.startsWith("buy") ? OrderType.BID : OrderType.ASK;
    final CurrencyPair currencyPair = adaptCurrencyPair(transaction.getMarket());

    final BigDecimal amount;
    final BigDecimal money;
    final int scale;

    if (currencyPair.equals(CurrencyPair.BTC_CNY)) {
      amount = transaction.getBtcAmount().abs();
      money = transaction.getCnyAmount().abs();
      scale = BTCChinaExchange.CNY_SCALE;
    }
    else if (currencyPair.equals(CurrencyPair.LTC_CNY)) {
      amount = transaction.getLtcAmount().abs();
      money = transaction.getCnyAmount().abs();
      scale = BTCChinaExchange.CNY_SCALE;
    }
    else if (currencyPair.equals(CurrencyPair.LTC_BTC)) {
      amount = transaction.getLtcAmount().abs();
      money = transaction.getBtcAmount().abs();
      scale = BTCChinaExchange.BTC_SCALE;
    }
    else {
      throw new IllegalArgumentException("Unknown currency pair: " + currencyPair);
    }

    final BigDecimal price = money.divide(amount, scale, RoundingMode.HALF_EVEN);
    final Date date = adaptDate(transaction.getDate());
    final String tradeId = String.valueOf(transaction.getId());

    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  /**
   * Adapt BTCChinaTransactions to Trades.
   */
  public static Trades adaptTransactions(List<BTCChinaTransaction> transactions) {

    List<Trade> tradeHistory = new ArrayList<Trade>(transactions.size());

    for (BTCChinaTransaction transaction : transactions) {
      Trade adaptTransaction = adaptTransaction(transaction);
      if (adaptTransaction != null) {
        tradeHistory.add(adaptTransaction);
      }
    }

    return new Trades(tradeHistory, TradeSortType.SortByID);
  }

  public static String adaptMarket(CurrencyPair currencyPair) {

    return currencyPair.baseSymbol.toLowerCase() + currencyPair.counterSymbol.toLowerCase();
  }

  public static CurrencyPair adaptCurrencyPairFromTickerMarketKey(String market) {

    return adaptCurrencyPair(market.substring(TICKER_MARKET_KEY_PREFIX_LENGTH));
  }

  public static CurrencyPair adaptCurrencyPairFromOrdersMarketKey(String market) {

    return adaptCurrencyPair(market.substring(ORDERS_MARKET_KEY_PREFIX_LENGTH));
  }

  public static CurrencyPair adaptCurrencyPair(String market) {

    return new CurrencyPair(market.substring(0, 3).toUpperCase(), market.substring(3).toUpperCase());
  }

  public static Date adaptDate(long date) {

    return DateUtils.fromMillisUtc(date * 1000L);
  }

  public static OrderType adaptOrderType(String type) {

    return type.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static BTCChinaOrderStatus adaptOrderStatus(String status) {

    return BTCChinaOrderStatus.valueOf(status.toUpperCase());
  }

}
