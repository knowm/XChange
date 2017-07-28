package org.knowm.xchange.hitbtc;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.hitbtc.dto.TransactionResponse;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTime;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrade;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrades;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HitbtcAdapters {

  public static final char DELIM = '_';

  private static final Map<String, FundingRecord.Type> FUNDING_TYPES = new HashMap<String, FundingRecord.Type>() {{
    put("exchangeToBank", null);//internal transfer
    put("bankToExchange", null);//internal transfer
    put("payin", FundingRecord.Type.DEPOSIT);
    put("payout", FundingRecord.Type.WITHDRAWAL);
  }};

  /**
   * Singleton
   */
  private HitbtcAdapters() {

  }

  public static Date adaptTime(HitbtcTime hitbtcTime) {

    return new Date(hitbtcTime.getTimestamp());
  }

  public static CurrencyPair adaptSymbol(String symbolString) {

    return CurrencyPairDeserializer.getCurrencyPairFromString(symbolString);
  }

  public static CurrencyPair adaptSymbol(HitbtcSymbol hitbtcSymbol) {

    return new CurrencyPair(hitbtcSymbol.getCommodity(), hitbtcSymbol.getCurrency());
  }

  /**
   * Adapts a HitbtcTicker to a Ticker Object
   *
   * @param hitbtcTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(HitbtcTicker hitbtcTicker, CurrencyPair currencyPair) {

    BigDecimal bid = hitbtcTicker.getBid();
    BigDecimal ask = hitbtcTicker.getAsk();
    BigDecimal high = hitbtcTicker.getHigh();
    BigDecimal low = hitbtcTicker.getLow();
    BigDecimal last = hitbtcTicker.getLast();
    BigDecimal volume = hitbtcTicker.getVolume();
    Date timestamp = new Date(hitbtcTicker.getTimestamp());

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();
  }

  public static List<Ticker> adaptTickers(Map<String, HitbtcTicker> hitbtcTickers) {

    List<Ticker> tickers = new ArrayList<>(hitbtcTickers.size());

    for (Map.Entry<String, HitbtcTicker> ticker : hitbtcTickers.entrySet()) {

      tickers.add(adaptTicker(ticker.getValue(), adaptSymbol(ticker.getKey())));
    }

    return tickers;
  }

  public static OrderBook adaptOrderBook(HitbtcOrderBook hitbtcOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptMarketOrderToLimitOrder(hitbtcOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(BigDecimal[][] hitbtcOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<>(hitbtcOrders.length);

    for (int i = 0; i < hitbtcOrders.length; i++) {
      BigDecimal[] hitbtcOrder = hitbtcOrders[i];

      BigDecimal price = hitbtcOrder[0];
      BigDecimal amount = hitbtcOrder[1];

      LimitOrder limitOrder = new LimitOrder(orderType, amount, currencyPair, null, null, price);
      orders.add(limitOrder);
    }

    return orders;
  }

  public static OrderType adaptSide(HitbtcTrade.HitbtcTradeSide side) {

    switch (side) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
      default:
        return null;
    }
  }

  public static Trades adaptTrades(HitbtcTrades hitbtcTrades, CurrencyPair currencyPair) {

    List<HitbtcTrade> allHitbtcTrades = hitbtcTrades.getHitbtcTrades();
    return adaptTrades(allHitbtcTrades, currencyPair);
  }

  public static Trades adaptTrades(List<? extends HitbtcTrade> allHitbtcTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(allHitbtcTrades.size());
    long lastTradeId = 0;
    for (int i = 0; i < allHitbtcTrades.size(); i++) {
      HitbtcTrade hitbtcTrade = allHitbtcTrades.get(i);

      Date timestamp = new Date(hitbtcTrade.getDate());
      BigDecimal price = hitbtcTrade.getPrice();
      BigDecimal amount = hitbtcTrade.getAmount();
      String tid = hitbtcTrade.getTid();
      long longTradeId = tid == null ? 0 : Long.parseLong(tid);
      if (longTradeId > lastTradeId) {
        lastTradeId = longTradeId;
      }
      OrderType orderType = adaptSide(hitbtcTrade.getSide());
      Trade trade = new Trade(orderType, amount, currencyPair, price, timestamp, tid);
      trades.add(trade);
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  public static OpenOrders adaptOpenOrders(HitbtcOrder[] openOrdersRaw) {

    List<LimitOrder> openOrders = new ArrayList<>(openOrdersRaw.length);

    for (int i = 0; i < openOrdersRaw.length; i++) {
      HitbtcOrder o = openOrdersRaw[i];

      OrderType type = adaptOrderType(o.getSide());

      LimitOrder order =
          new LimitOrder(
              type,
              o.getOrderQuantity(),
              adaptSymbol(o.getSymbol()),
              o.getClientOrderId(),
              new Date(o.getLastTimestamp()),
              o.getOrderPrice());

      openOrders.add(order);
    }

    return new OpenOrders(openOrders);
  }

  public static OrderType adaptOrderType(String side) {

    return side.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static UserTrades adaptTradeHistory(HitbtcOwnTrade[] tradeHistoryRaw, ExchangeMetaData metaData) {

    List<UserTrade> trades = new ArrayList<>(tradeHistoryRaw.length);
    for (int i = 0; i < tradeHistoryRaw.length; i++) {
      HitbtcOwnTrade t = tradeHistoryRaw[i];
      OrderType type = adaptOrderType(t.getSide());

      CurrencyPair pair = adaptSymbol(t.getSymbol());

      // minimumAmount is equal to lot size
      BigDecimal tradableAmount = t.getExecQuantity().multiply(metaData.getCurrencyPairs().get(pair).getMinimumAmount());
      Date timestamp = new Date(t.getTimestamp());
      String id = Long.toString(t.getTradeId());

      UserTrade trade = new UserTrade(type, tradableAmount, pair, t.getExecPrice(), timestamp, id, t.getClientOrderId(), t.getFee(),
          Currency.getInstance(pair.counter.getCurrencyCode()));

      trades.add(trade);
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(HitbtcBalance[] walletRaw) {

    List<Balance> balances = new ArrayList<>(walletRaw.length);

    for (HitbtcBalance balanceRaw : walletRaw) {

      Balance balance = new Balance(Currency.getInstance(balanceRaw.getCurrencyCode()), null, balanceRaw.getCash(), balanceRaw.getReserved());
      balances.add(balance);

    }
    return new Wallet(balances);
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return pair == null ? null : pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

  public static String createOrderId(Order order, long nonce) {

    if (order.getId() == null || "".equals(order.getId())) {
      // encoding side in client order id
      return order.getType().name().substring(0, 1) + DELIM + adaptCurrencyPair(order.getCurrencyPair()) + DELIM + nonce;
    } else {
      return order.getId();
    }
  }

  public static OrderType readOrderType(String orderId) {

    return orderId.charAt(0) == 'A' ? OrderType.ASK : OrderType.BID;
  }

  public static String readSymbol(String orderId) {
    int start = orderId.indexOf(DELIM);
    if (start != -1) {
      int end = orderId.indexOf(DELIM, start + 1);
      if (end != -1) {
        return orderId.substring(start + 1, end);
      }
    }
    return "";
  }

  public static HitbtcTrade.HitbtcTradeSide getSide(OrderType type) {

    return type == OrderType.BID ? HitbtcTrade.HitbtcTradeSide.BUY : HitbtcTrade.HitbtcTradeSide.SELL;
  }

  public static ExchangeMetaData adaptToExchangeMetaData(HitbtcSymbols symbols, Map<Currency, CurrencyMetaData> currencies) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    if (symbols != null) {
      for (HitbtcSymbol symbol : symbols.getHitbtcSymbols()) {
        CurrencyPair pair = adaptSymbol(symbol);
        CurrencyPairMetaData meta = new CurrencyPairMetaData(symbol.getTakeLiquidityRate(), symbol.getLot(), null, symbol.getStep().scale());

        currencyPairs.put(pair, meta);
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }

  public static FundingRecord adapt(TransactionResponse transaction) {
    FundingRecord.Type type = FUNDING_TYPES.get(transaction.type);

    FundingRecord.Status status = transaction.status.equals("success") ? FundingRecord.Status.COMPLETE : FundingRecord.Status.FAILED;//todo: find out if there are more statuses

    return new FundingRecord(
        transaction.bitcoinAddress,
        DateUtils.fromUnixTime(transaction.finished),
        Currency.getInstanceNoCreate(transaction.currencyCodeTo),
        transaction.amountTo,
        transaction.id,
        transaction.destinationData,
        type,
        status,
        null,
        transaction.commissionPercent,
        transaction.type + " " + transaction.status
    );
  }
}
