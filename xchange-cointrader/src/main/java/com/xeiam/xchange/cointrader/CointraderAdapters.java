package com.xeiam.xchange.cointrader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.cointrader.dto.account.CointraderBalance;
import com.xeiam.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import com.xeiam.xchange.cointrader.dto.trade.CointraderOrder;
import com.xeiam.xchange.cointrader.dto.trade.CointraderUserTrade;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.account.Balance;

public final class CointraderAdapters {

  public static final Comparator<LimitOrder> ASK_COMPARATOR = new Comparator<LimitOrder>() {
    @Override
    public int compare(LimitOrder o1, LimitOrder o2) {
      return o1.getLimitPrice().compareTo(o2.getLimitPrice());
    }
  };
  public static final Comparator<LimitOrder> BID_COMPARATOR = new Comparator<LimitOrder>() {
    @Override
    public int compare(LimitOrder o1, LimitOrder o2) {
      return o2.getLimitPrice().compareTo(o1.getLimitPrice());
    }
  };

  private CointraderAdapters() {
  }

  public static Wallet adaptWallet(Map<String, CointraderBalance> balances) {
    List<Balance> wallet = new ArrayList<Balance>(balances.size());
    for (String currency : balances.keySet()) {
      Currency xchangeCurrency = Currency.getInstance(currency);
      CointraderBalance blc = balances.get(currency);
      wallet.add(new Balance(xchangeCurrency, blc.getTotal(), blc.getAvailable()));

    }
    return new Wallet(wallet);
  }

  public static OrderBook adaptOrderBook(CointraderOrderBook cointraderOrderBook) {
    List<LimitOrder> asks = new ArrayList<LimitOrder>();
    List<LimitOrder> bids = new ArrayList<LimitOrder>();
    for (CointraderOrderBook.Entry obe : cointraderOrderBook.getData()) {
      if (CointraderOrder.Type.Buy.equals(obe.getType())) {
        bids.add(new LimitOrder(Order.OrderType.BID, obe.getQuantity(), obe.getCurrencyPair(), null, obe.getCreated(), obe.getPrice()));
      } else {
        asks.add(new LimitOrder(Order.OrderType.ASK, obe.getQuantity(), obe.getCurrencyPair(), null, obe.getCreated(), obe.getPrice()));
      }
    }
    Collections.sort(bids, BID_COMPARATOR);
    Collections.sort(asks, ASK_COMPARATOR);
    return new OrderBook(new Date(), asks, bids);
  }

  public static LimitOrder adaptOrder(CointraderOrder o) {
    return new LimitOrder(adaptOrderType(o.getType()), o.getQuantity(), o.getCurrencyPair(), Long.toString(o.getId()), o.getCreated(), o.getPrice());
  }

  public static UserTrades adaptTradeHistory(CointraderUserTrade[] cointraderUserTrades) {
    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (CointraderUserTrade cointraderUserTrade : cointraderUserTrades) {
      lastTradeId = Math.max(lastTradeId, cointraderUserTrade.getTradeId());
      trades.add(adaptTrade(cointraderUserTrade));
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static UserTrade adaptTrade(CointraderUserTrade cointraderUserTrade) {
    CurrencyPair currencyPair = cointraderUserTrade.getCurrencyPair();
    return new UserTrade(adaptOrderType(cointraderUserTrade.getType()), cointraderUserTrade.getQuantity(), currencyPair,
        cointraderUserTrade.getPrice().abs(), cointraderUserTrade.getExecuted(), String.valueOf(cointraderUserTrade.getTradeId()),
        String.valueOf(cointraderUserTrade.getOrderId()), cointraderUserTrade.getFee(),
        cointraderUserTrade.getType() == CointraderOrder.Type.Buy ? currencyPair.counter.getCurrencyCode() : currencyPair.base.getCurrencyCode());
  }

  public static Order.OrderType adaptOrderType(CointraderOrder.Type orderType) {
    return orderType.equals(CointraderOrder.Type.Buy) ? Order.OrderType.BID : Order.OrderType.ASK;
  }
}
