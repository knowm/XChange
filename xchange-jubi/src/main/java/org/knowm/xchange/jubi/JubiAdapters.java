package org.knowm.xchange.jubi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.jubi.dto.account.JubiBalance;
import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;
import org.knowm.xchange.jubi.dto.marketdata.JubiTrade;
import org.knowm.xchange.jubi.dto.trade.JubiOrder;
import org.knowm.xchange.jubi.dto.trade.JubiOrderHistory;
import org.knowm.xchange.jubi.dto.trade.JubiOrderType;

/**
 * Created by Yingzhe on 3/16/2015.
 * Adapter for market data.
 */
public class JubiAdapters {
  /**
   * private constructor
   */
  private JubiAdapters() {
  }

  public static Ticker adaptTicker(JubiTicker ticker, CurrencyPair currencyPair) {
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal bid = ticker.getBuy();
    BigDecimal ask = ticker.getSell();
    BigDecimal last = ticker.getLast();
    BigDecimal volume = ticker.getVol();
    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).bid(bid).ask(ask).volume(volume).build();
  }

  public static Trades adaptTrades(JubiTrade[] jubiTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();

    for (JubiTrade jubiTrade : jubiTrades) {

      Order.OrderType orderType = jubiTrade.getType().equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
      Trade trade = new Trade(orderType, jubiTrade.getAmount(), currencyPair, jubiTrade.getPrice(), new Date(jubiTrade.getDate() * 1000),
          jubiTrade.getTid());

      tradeList.add(trade);
    }

    return new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(JubiBalance jubiBalance, String userName) {

    List<Balance> balances = new ArrayList<>();
    for (Map.Entry<String, BigDecimal> funds : jubiBalance.getAvailableFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      BigDecimal amount = funds.getValue();
      BigDecimal locked = jubiBalance.getLockedFunds().get(funds.getKey());
      balances.add(new Balance(currency, null, amount, locked));
    }
    return new AccountInfo(userName, new Wallet(jubiBalance.getUid(), userName, balances));
  }

  public static UserTrade adaptUserTrade(JubiOrder jubiOrder, CurrencyPair currencyPair) {
    final Order.OrderType orderType = jubiOrder.getType() == JubiOrderType.Buy ? Order.OrderType.BID : Order.OrderType.ASK;
    return new UserTrade(orderType, jubiOrder.getAmountOriginal(), currencyPair,
        jubiOrder.getPrice(), jubiOrder.getDatetime(), jubiOrder.getId().toPlainString(),
        null, null, null);
  }

  public static UserTrades adaptUserTrades(JubiOrderHistory jubiOrderHistory, CurrencyPair currencyPair) {
    List<UserTrade> trades = new ArrayList<>();
    if (currencyPair != null && jubiOrderHistory != null && jubiOrderHistory.getResult().isSuccess()) {
      BigDecimal lastTradeId = BigDecimal.ZERO;
      for (JubiOrder jubiOrder : jubiOrderHistory.getOrderList()) {
        BigDecimal tradeId = jubiOrder.getId();
        if (tradeId.compareTo(lastTradeId) > 0) {
          lastTradeId = tradeId;
        }
        trades.add(adaptUserTrade(jubiOrder, currencyPair));
      }
      return new UserTrades(trades, lastTradeId.longValue(), Trades.TradeSortType.SortByID);
    } else {
      return new UserTrades(trades, Trades.TradeSortType.SortByID);
    }
  }

  public static LimitOrder adaptLimitOrder(JubiOrder jubiOrder, CurrencyPair currencyPair) {
    final Order.OrderType orderType = jubiOrder.getType() == JubiOrderType.Buy ? Order.OrderType.BID : Order.OrderType.ASK;
    final BigDecimal cumulativeAmount = jubiOrder.getAmountOriginal().subtract(jubiOrder.getAmountOutstanding());
    return new LimitOrder(orderType, jubiOrder.getAmountOriginal(), currencyPair, jubiOrder.getId().toPlainString(),
        jubiOrder.getDatetime(), jubiOrder.getPrice(), null, cumulativeAmount, null);
  }

  public static OpenOrders adaptOpenOrders(JubiOrderHistory jubiOrderHistory, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    if (currencyPair != null && jubiOrderHistory != null && jubiOrderHistory.getResult().isSuccess()) {
      for (JubiOrder jubiOrder : jubiOrderHistory.getOrderList()) {
        limitOrders.add(adaptLimitOrder(jubiOrder, currencyPair));
      }

    }
    return new OpenOrders(limitOrders);
  }
}
