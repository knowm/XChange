package com.xeiam.xchange.bitmarket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryOperation;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryOperations;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketHistoryTrades;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrder;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;

/**
 * @author kpysniak, kfonal
 */
public class BitMarketAdapters {

  /**
   * Singleton
   */
  private BitMarketAdapters() {

  }

  /**
   * Adapts BitMarketBalance to Wallet
   *
   * @param balance
   * @return
   */
  public static Wallet adaptWallet(BitMarketBalance balance) {

    List<Balance> balances = new ArrayList<Balance>(balance.getAvailable().size());

    for (Map.Entry<String, BigDecimal> entry : balance.getAvailable().entrySet()) {
      Currency currency = Currency.getInstance(entry.getKey());
      BigDecimal frozen = balance.getBlocked().containsKey(entry.getKey()) ? balance.getBlocked().get(entry.getKey()) : new BigDecimal("0");
      BigDecimal available = entry.getValue();
      balances.add(new Balance(currency, available.add(frozen), available, frozen));
    }

    return new Wallet(balances);
  }

  /**
   * Adapts BitMarket ticker to Ticker.
   *
   * @param bitMarketTicker
   * @param currencyPair
   * @return
   */
  public static Ticker adaptTicker(BitMarketTicker bitMarketTicker, CurrencyPair currencyPair) {

    BigDecimal bid = bitMarketTicker.getBid();
    BigDecimal ask = bitMarketTicker.getAsk();
    BigDecimal high = bitMarketTicker.getHigh();
    BigDecimal low = bitMarketTicker.getLow();
    BigDecimal volume = bitMarketTicker.getVolume();
    BigDecimal last = bitMarketTicker.getLast();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }

  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BitMarketOrderBook bitMarketOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook = new OrderBook(null, transformArrayToLimitOrders(bitMarketOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(bitMarketOrderBook.getBids(), OrderType.BID, currencyPair));

    return orderBook;
  }

  public static Trades adaptTrades(BitMarketTrade[] bitMarketTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<Trade>();

    for (BitMarketTrade bitMarketTrade : bitMarketTrades) {

      Trade trade = new Trade(OrderType.BID, bitMarketTrade.getAmount(), currencyPair, bitMarketTrade.getPrice(), new Date(bitMarketTrade.getDate()),
          bitMarketTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }

  public static OpenOrders adaptOpenOrders(Map<String, Map<String, List<BitMarketOrder>>> ordersMap) {

    List<LimitOrder> orders = new ArrayList<LimitOrder>();

    for (Map.Entry<String, Map<String, List<BitMarketOrder>>> rootEntry : ordersMap.entrySet()) {
      for (Map.Entry<String, List<BitMarketOrder>> entry : rootEntry.getValue().entrySet()) {
        for (BitMarketOrder bitMarketOrder : entry.getValue()) {
          orders.add(createOrder(bitMarketOrder));
        }
      }
    }

    return new OpenOrders(orders);
  }

  private static LimitOrder createOrder(BitMarketOrder bitMarketOrder) {

    return new LimitOrder(bitMarketOrder.getType(), bitMarketOrder.getAmount(), bitMarketOrder.getCurrencyPair(),
        String.valueOf(bitMarketOrder.getId()), bitMarketOrder.getTimestamp(), bitMarketOrder.getRate());
  }

  public static UserTrades adaptTradeHistory(BitMarketHistoryTrades historyTrades, BitMarketHistoryOperations historyOperations) {

    List<UserTrade> trades = new ArrayList<UserTrade>();

    for (BitMarketHistoryTrade trade : historyTrades.getTrades()) {
      trades.add(createHistoryTrade(trade, historyOperations));
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  private static UserTrade createHistoryTrade(BitMarketHistoryTrade trade, BitMarketHistoryOperations operations) {

    //deduce commission currency
    String commissionCurrency = BitMarketUtils.BitMarketOrderTypeToOrderType(trade.getType()) == OrderType.ASK ? trade.getCurrencyCrypto()
        : trade.getCurrencyFiat();

    //find in history operations - the operation which time match to time of given trade
    BitMarketHistoryOperation tradeOperation = null;
    for (BitMarketHistoryOperation operation : operations.getOperations()) {
      if (operation.getType().equals("trade") && operation.getCurrency().equals(commissionCurrency) && operation.getTime() == trade.getTime()) {
        tradeOperation = operation;
        break; //first matching history operation is taking into consideration only
      }
    }

    return new UserTrade(BitMarketUtils.BitMarketOrderTypeToOrderType(trade.getType()), trade.getAmountCrypto(),
        new CurrencyPair(trade.getCurrencyCrypto(), trade.getCurrencyFiat()), trade.getRate(), trade.getTimestamp(), String.valueOf(trade.getId()),
        tradeOperation != null ? String.valueOf(tradeOperation.getId()) : null, tradeOperation != null ? tradeOperation.getCommission() : null,
        commissionCurrency);
  }
}
