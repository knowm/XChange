package org.knowm.xchange.cointrader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.cointrader.dto.account.CointraderBalance;
import org.knowm.xchange.cointrader.dto.marketdata.CointraderOrderBook;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrder;
import org.knowm.xchange.cointrader.dto.trade.CointraderUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

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
        String.valueOf(cointraderUserTrade.getOrderId()), cointraderUserTrade.getFee(), cointraderUserTrade.getType() == CointraderOrder.Type.Buy
            ? Currency.getInstance(currencyPair.counter.getCurrencyCode()) : Currency.getInstance(currencyPair.base.getCurrencyCode()));
  }

  public static Order.OrderType adaptOrderType(CointraderOrder.Type orderType) {
    return orderType.equals(CointraderOrder.Type.Buy) ? Order.OrderType.BID : Order.OrderType.ASK;
  }
}
