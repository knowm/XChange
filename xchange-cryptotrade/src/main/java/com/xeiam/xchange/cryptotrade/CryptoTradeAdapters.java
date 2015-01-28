package com.xeiam.xchange.cryptotrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.account.CryptoTradeAccountInfo;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicOrder;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrder;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeOrders;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades.CryptoTradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from CryptoTrade DTOs to XChange DTOs
 */
public final class CryptoTradeAdapters {

  private CryptoTradeAdapters() {

  }

  public static LimitOrder adaptOrder(CryptoTradePublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<CryptoTradePublicOrder> cryptoTradeOrders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (CryptoTradePublicOrder order : cryptoTradeOrders)
      limitOrders.add(adaptOrder(order, currencyPair, orderType));

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(CurrencyPair currencyPair, CryptoTradeDepth cryptoTradeDepth) {

    List<LimitOrder> asks = CryptoTradeAdapters.adaptOrders(cryptoTradeDepth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = CryptoTradeAdapters.adaptOrders(cryptoTradeDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static AccountInfo adaptAccountInfo(String userName, CryptoTradeAccountInfo accountInfo) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    for (Entry<String, BigDecimal> fundsEntry : accountInfo.getFunds().entrySet())
      wallets.add(new Wallet(fundsEntry.getKey().toUpperCase(), fundsEntry.getValue()));

    return new AccountInfo(userName, wallets);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, CryptoTradeTicker cryptoTradeTicker) {

    BigDecimal ask = cryptoTradeTicker.getMinAsk();
    BigDecimal bid = cryptoTradeTicker.getMaxBid();
    BigDecimal last = cryptoTradeTicker.getLast();
    BigDecimal low = cryptoTradeTicker.getLow();
    BigDecimal high = cryptoTradeTicker.getHigh();
    BigDecimal volume = cryptoTradeTicker.getVolumeTradeCurrency();

    return new Ticker.Builder().currencyPair(currencyPair).ask(ask).bid(bid).last(last).low(low).high(high).volume(volume).build();
  }

  public static OrderType adaptOrderType(CryptoTradeOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(CryptoTradeOrderType.Buy)) ? OrderType.BID : OrderType.ASK;
  }

  public static LimitOrder adaptOrder(CryptoTradeOrder order) {

    Date timestamp = new Date(order.getOrderDate());
    OrderType orderType = adaptOrderType(order.getType());

    return new LimitOrder(orderType, order.getRemainingAmount(), order.getCurrencyPair(), String.valueOf(order.getId()), timestamp, order.getRate());
  }

  public static OpenOrders adaptOpenOrders(CryptoTradeOrders cryptoTradeOrders) {

    List<LimitOrder> orderList = new ArrayList<LimitOrder>();
    for (CryptoTradeOrder cryptoTradeOrder : cryptoTradeOrders.getOrders()) {
      // TODO Change to check cryptoTradeOrder.getStatus() once all statuses are known.
      if (cryptoTradeOrder.getRemainingAmount().compareTo(BigDecimal.ZERO) > 0) {
        LimitOrder adaptedOrder = adaptOrder(cryptoTradeOrder);
        orderList.add(adaptedOrder);
      }
    }

    return new OpenOrders(orderList);
  }

  public static UserTrade adaptTrade(CryptoTradeTrade trade) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = new Date(trade.getTimestamp());

    return new UserTrade(orderType, trade.getAmount(), trade.getCurrencyPair(), trade.getRate(), timestamp, String.valueOf(trade.getId()),
        String.valueOf(trade.getMyOrder()), null, null);
  }

  public static UserTrades adaptTrades(CryptoTradeTrades cryptoTradeTrades) {

    List<UserTrade> tradeList = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (CryptoTradeTrade cryptoTradeTrade : cryptoTradeTrades.getTrades()) {
      long tradeId = cryptoTradeTrade.getId();
      if (tradeId > lastTradeId)
        lastTradeId = tradeId;
      UserTrade trade = adaptTrade(cryptoTradeTrade);
      tradeList.add(trade);
    }

    return new UserTrades(tradeList, lastTradeId, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptPublicTrade(CurrencyPair currencyPair, CryptoTradePublicTrade trade) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = new Date(trade.getTimestamp() * 1000);

    return new Trade(orderType, trade.getOrderAmount(), currencyPair, trade.getRate(), timestamp, String.valueOf(trade.getId()));
  }

  public static Trades adaptPublicTradeHistory(CurrencyPair currencyPair, List<CryptoTradePublicTrade> publicTradeHistory) {

    List<Trade> tradeList = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (CryptoTradePublicTrade cryptoTradeTrade : publicTradeHistory) {
      long tradeId = cryptoTradeTrade.getId();
      if (tradeId > lastTradeId)
        lastTradeId = tradeId;
      Trade trade = adaptPublicTrade(currencyPair, cryptoTradeTrade);
      tradeList.add(trade);
    }

    return new Trades(tradeList, lastTradeId, TradeSortType.SortByID);
  }

}
