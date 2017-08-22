package org.knowm.xchange.hitbtc.v2.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderLimit;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSide;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class HitbtcAdapters {

  public static final char DELIMITER = '_';

  private static final Map<String, FundingRecord.Type> FUNDING_TYPES = new HashMap<String, FundingRecord.Type>() {{
    put("exchangeToBank", null);//internal transfer
    put("bankToExchange", null);//internal transfer
    put("payin", FundingRecord.Type.DEPOSIT);
    put("payout", FundingRecord.Type.WITHDRAWAL);
  }};

  public static CurrencyPair adaptSymbol(String symbolString) {

    return CurrencyPairDeserializer.getCurrencyPairFromString(symbolString);
  }

  public static CurrencyPair adaptSymbol(HitbtcSymbol hitbtcSymbol) {

    return new CurrencyPair(hitbtcSymbol.getBaseCurrency(), hitbtcSymbol.getQuoteCurrency());
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
    Date timestamp = hitbtcTicker.getTimestamp();

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

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(HitbtcOrderLimit[] hitbtcOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<>(hitbtcOrders.length);

    for (HitbtcOrderLimit hitbtcOrderLimit : hitbtcOrders) {
      LimitOrder limitOrder = new LimitOrder(orderType, hitbtcOrderLimit.getSize(), currencyPair, null, null, hitbtcOrderLimit.getPrice());
      orders.add(limitOrder);
    }

    return orders;
  }

  public static OrderType adaptSide(HitbtcSide side) {

    switch (side) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
      default:
        return null;
    }
  }

  public static Trades adaptTrades(List<? extends HitbtcTrade> allHitbtcTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(allHitbtcTrades.size());
    long lastTradeId = 0;
    for (int i = 0; i < allHitbtcTrades.size(); i++) {
      HitbtcTrade hitbtcTrade = allHitbtcTrades.get(i);

      Date timestamp = hitbtcTrade.getTimestamp();
      BigDecimal price = hitbtcTrade.getPrice();
      BigDecimal amount = hitbtcTrade.getQuantity();
      String tid = hitbtcTrade.getId();
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

//  public static OpenOrders adaptOpenOrders(List<HitbtcOrder> openOrdersRaw) {
//
//    List<LimitOrder> openOrders = new ArrayList<>(openOrdersRaw.size());
//
//    for (HitbtcOrder hitbtcOrder : openOrdersRaw) {
//
//      OrderType type = adaptOrderType(hitbtcOrder.getSide().getValue());
//
//      LimitOrder order =
//          new LimitOrder(
//              type,
//              hitbtcOrder.getQuantity(),
//              adaptSymbol(hitbtcOrder.getSymbol()),
//              hitbtcOrder.getClientOrderId(),
//              hitbtcOrder.getCreatedAt(),
//              hitbtcOrder.getPrice());
//
//      openOrders.add(order);
//    }
//
//    return new OpenOrders(openOrders);
//  }

  public static OrderType adaptOrderType(String side) {

    return side.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

//  public static UserTrades adaptTradeHistory(List<HitbtcOwnTrade> tradeHistoryRaw, ExchangeMetaData metaData) {
//
//    List<UserTrade> trades = new ArrayList<>(tradeHistoryRaw.size());
//    for (HitbtcOwnTrade t : tradeHistoryRaw) {
//      OrderType type = adaptOrderType(t.getSide().getValue());
//
//      //TODO no longer available... need to fix
//      CurrencyPair pair = adaptSymbol("BTCUSD");
//
//      // minimumAmount is equal to lot size
//      BigDecimal tradableAmount = t.getQuantity().multiply(metaData.getCurrencyPairs().get(pair).getMinimumAmount());
//      Date timestamp = t.getTimestamp();
//      String id = Long.toString(t.getId());
//
//      UserTrade trade = new UserTrade(type, tradableAmount, pair, t.getPrice(), timestamp, id, t.getClientOrderId(), t.getFee(),
//          Currency.getInstance(pair.counter.getCurrencyCode()));
//
//      trades.add(trade);
//    }
//
//    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
//  }
//
//  public static Wallet adaptWallet(HitbtcBalance[] hitbtcBalances) {
//
//    List<Balance> balances = new ArrayList<>(hitbtcBalances.length);
//
//    for (HitbtcBalance balanceRaw : hitbtcBalances) {
//
//      Currency currency = Currency.getInstance(balanceRaw.getCurrency());
//      Balance balance =
//          new Balance(currency, balanceRaw.getAvailable(), balanceRaw.getAvailable().subtract(balanceRaw.getReserved()), balanceRaw.getReserved());
//      balances.add(balance);
//    }
//    return new Wallet(balances);
//  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return pair == null ? null : pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

//  public static String createOrderId(Order order, long nonce) {
//
//    if (order.getId() == null || "".equals(order.getId())) {
//      // encoding side in client order id
//      return order.getType().name().substring(0, 1) + DELIMITER + adaptCurrencyPair(order.getCurrencyPair()) + DELIMITER + nonce;
//    } else {
//      return order.getId();
//    }
//  }
//
//  public static OrderType readOrderType(String orderId) {
//
//    return orderId.charAt(0) == 'A' ? OrderType.ASK : OrderType.BID;
//  }
//
//  public static String readSymbol(String orderId) {
//    int start = orderId.indexOf(DELIMITER);
//    if (start != -1) {
//      int end = orderId.indexOf(DELIMITER, start + 1);
//      if (end != -1) {
//        return orderId.substring(start + 1, end);
//      }
//    }
//    return "";
//  }
//
//  public static HitbtcSide getSide(OrderType type) {
//
//    return type == OrderType.BID ? HitbtcSide.BUY : HitbtcSide.SELL;
//  }

  public static ExchangeMetaData adaptToExchangeMetaData(List<HitbtcSymbol> symbols, Map<Currency, CurrencyMetaData> currencies) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    if (symbols != null) {
      for (HitbtcSymbol symbol : symbols) {
        CurrencyPair pair = adaptSymbol(symbol);
        //TODO double check
        CurrencyPairMetaData meta = new CurrencyPairMetaData(symbol.getTakeLiquidityRate(), symbol.getTakeLiquidityRate(), null, null);

        currencyPairs.put(pair, meta);
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }


//
//  public static FundingRecord adapt(TransactionResponse transaction) {
//    FundingRecord.Type type = FUNDING_TYPES.get(transaction.type);
//
//    FundingRecord.Status status = transaction.status.equals("success") ? FundingRecord.Status.COMPLETE : FundingRecord.Status.FAILED;//todo: find out if there are more statuses
//
//    return new FundingRecord(
//        transaction.bitcoinAddress,
//        DateUtils.fromUnixTime(transaction.finished),
//        Currency.getInstanceNoCreate(transaction.currencyCodeTo),
//        transaction.amountTo,
//        transaction.id,
//        transaction.destinationData,
//        type,
//        status,
//        null,
//        transaction.commissionPercent,
//        transaction.type + " " + transaction.status
//    );
//  }

}