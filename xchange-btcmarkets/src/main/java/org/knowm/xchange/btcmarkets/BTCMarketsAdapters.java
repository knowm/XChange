package org.knowm.xchange.btcmarkets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder.Side;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public final class BTCMarketsAdapters {

  public static final Comparator<LimitOrder> ASK_COMPARATOR =
      (o1, o2) -> o1.getLimitPrice().compareTo(o2.getLimitPrice());
  public static final Comparator<LimitOrder> BID_COMPARATOR =
      (o1, o2) -> o2.getLimitPrice().compareTo(o1.getLimitPrice());

  private BTCMarketsAdapters() {}

  public static Wallet adaptWallet(List<BTCMarketsBalance> balances) {
    List<Balance> wallets = new ArrayList<>(balances.size());
    for (BTCMarketsBalance blc : balances) {
      final Currency currency = Currency.valueOf(blc.getCurrency());
      BigDecimal total = blc.getBalance();
      BigDecimal available = blc.getAvailable();
      wallets.add(
          new Balance.Builder()
              .setCurrency(currency)
              .setTotal(total)
              .setAvailable(available)
              .setFrozen(total.add(available.negate()))
              .createBalance());
    }
    return Wallet.build(wallets);
  }

  public static OrderBook adaptOrderBook(
      BTCMarketsOrderBook btcmarketsOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        createOrders(OrderType.ASK, btcmarketsOrderBook.getAsks(), currencyPair);
    List<LimitOrder> bids =
        createOrders(OrderType.BID, btcmarketsOrderBook.getBids(), currencyPair);
    bids.sort(BID_COMPARATOR);
    asks.sort(ASK_COMPARATOR);
    return new OrderBook(btcmarketsOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      OrderType orderType, List<BigDecimal[]> orders, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BigDecimal[] o : orders) {
      limitOrders.add(new LimitOrder(orderType, o[1], currencyPair, null, null, o[0]));
    }
    return limitOrders;
  }

  public static LimitOrder adaptOrder(BTCMarketsOrder o) {
    return new LimitOrder(
        adaptOrderType(o.getOrderSide()),
        o.getVolume(),
        CurrencyPair.build(o.getInstrument(), o.getCurrency()),
        Long.toString(o.getId()),
        o.getCreationTime(),
        o.getPrice());
  }

  public static UserTrades adaptTradeHistory(
      List<BTCMarketsUserTrade> btcmarketsUserTrades, CurrencyPair currencyPair) {
    List<UserTrade> trades = new ArrayList<>();
    for (BTCMarketsUserTrade btcmarketsUserTrade : btcmarketsUserTrades) {
      trades.add(adaptTrade(btcmarketsUserTrade, currencyPair));
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static UserTrade adaptTrade(BTCMarketsUserTrade trade, CurrencyPair currencyPair) {
    final OrderType type = adaptOrderType(trade.getSide());
    final String tradeId = Long.toString(trade.getId());
    final Long orderId = trade.getOrderId();
    final String feeCurrency = currencyPair.getBase().getCurrencyCode();
    return new UserTrade(
        type,
        trade.getVolume(),
        currencyPair,
        trade.getPrice().abs(),
        trade.getCreationTime(),
        tradeId,
        String.valueOf(orderId),
        trade.getFee(),
        Currency.valueOf(feeCurrency));
  }

  public static OrderType adaptOrderType(Side orderType) {
    return orderType.equals(Side.Bid) ? OrderType.BID : OrderType.ASK;
  }

  public static OpenOrders adaptOpenOrders(BTCMarketsOrders openOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BTCMarketsOrder btcmarketsOrder : openOrders.getOrders()) {
      limitOrders.add(adaptOrder(btcmarketsOrder));
    }
    return new OpenOrders(limitOrders);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, BTCMarketsTicker t) {
    return new Builder()
        .currencyPair(currencyPair)
        .last(t.getLastPrice())
        .bid(t.getBestBid())
        .ask(t.getBestAsk())
        .timestamp(t.getTimestamp())
        .build();
  }
}
