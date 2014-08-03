package com.xeiam.xchange.btcchina;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.BTCChinaValue;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
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

  /**
   * private Constructor
   */
  private BTCChinaAdapters() {

  }

  /**
   * Adapts a List of btcchinaOrders to a List of LimitOrders
   */
  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> btcchinaOrders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(btcchinaOrders.size());

    for (BigDecimal[] btcchinaOrder : btcchinaOrders) {
      limitOrders.add(adaptOrder(btcchinaOrder[1], btcchinaOrder[0], currencyPair, orderType));
    }

    return limitOrders;
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
    Date date = DateUtils.fromMillisUtc(btcChinaTrade.getDate() * 1000L);
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

    BigDecimal last = btcChinaTicker.getTicker().getLast();
    BigDecimal high = btcChinaTicker.getTicker().getHigh();
    BigDecimal low = btcChinaTicker.getTicker().getLow();
    BigDecimal buy = btcChinaTicker.getTicker().getBuy();
    BigDecimal sell = btcChinaTicker.getTicker().getSell();
    BigDecimal volume = btcChinaTicker.getTicker().getVol();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withBid(buy).withAsk(sell).withVolume(volume).build();
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

  public static List<LimitOrder> adaptOrders(List<BTCChinaOrder> orders, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());

    for (BTCChinaOrder order : orders) {
      LimitOrder limitOrder = adaptLimitOrder(order, currencyPair);
      limitOrders.add(limitOrder);
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
    Date date = new Date(order.getDate() * 1000);
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
    final CurrencyPair currencyPair = adaptCurrencyPair(transaction.getMarket().toUpperCase());

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
    final Date date = DateUtils.fromMillisUtc(transaction.getDate() * 1000L);
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

    return adaptCurrencyPair(market.substring(7));
  }

  public static CurrencyPair adaptCurrencyPair(String market) {

    return new CurrencyPair(market.substring(0, 3), market.substring(3));
  }
}
