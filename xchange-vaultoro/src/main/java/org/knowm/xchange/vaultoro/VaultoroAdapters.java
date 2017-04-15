package org.knowm.xchange.vaultoro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.vaultoro.dto.account.VaultoroBalance;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroOrder;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroOrderBook;
import org.knowm.xchange.vaultoro.dto.marketdata.VaultoroTrade;
import org.knowm.xchange.vaultoro.dto.trade.VaultoroOpenOrder;

/**
 * Various adapters for converting from Vaultoro DTOs to XChange DTOs
 */
public final class VaultoroAdapters {

  public static Ticker adaptVaultoroLatest(BigDecimal latest) {

    return new Ticker.Builder().last(latest).build();
  }

  public static OrderBook adaptVaultoroOrderBook(VaultoroOrderBook vaultoroOrderBook, CurrencyPair currencyPair) {

    List<VaultoroOrder> vaultoroBids = vaultoroOrderBook.getBuys();
    List<VaultoroOrder> vaultoroAsks = vaultoroOrderBook.getSells();

    List<LimitOrder> asks = new ArrayList<>();

    for (VaultoroOrder vaultoroOrder : vaultoroAsks) {
      asks.add(new LimitOrder.Builder(OrderType.ASK, currencyPair).limitPrice(vaultoroOrder.getGoldPrice())
          .tradableAmount(vaultoroOrder.getGoldAmount()).build());
    }

    List<LimitOrder> bids = new ArrayList<>();

    for (VaultoroOrder vaultoroOrder : vaultoroBids) {
      bids.add(new LimitOrder.Builder(OrderType.BID, currencyPair).limitPrice(vaultoroOrder.getGoldPrice())
          .tradableAmount(vaultoroOrder.getGoldAmount()).build());
    }

    return new OrderBook(null, asks, bids);
  }

  public static Trades adaptVaultoroTransactions(List<VaultoroTrade> vaultoroTransactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();

    for (VaultoroTrade vaultoroTrade : vaultoroTransactions) {
      Date date = VaultoroUtils.parseDate(vaultoroTrade.getTime());
      trades.add(new Trade.Builder().timestamp(date).currencyPair(currencyPair).price(vaultoroTrade.getGoldPrice())
          .tradableAmount(vaultoroTrade.getGoldAmount()).build());
    }
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptVaultoroBalances(List<VaultoroBalance> vaultoroBalances) {

    List<Balance> balances = new ArrayList<>();

    for (VaultoroBalance vaultoroBalance : vaultoroBalances) {
      balances.add(adaptVaultoroBalance(vaultoroBalance));
    }

    return new AccountInfo(new Wallet(balances));
  }

  public static Balance adaptVaultoroBalance(VaultoroBalance vaultoroBalance) {

    return new Balance(new Currency(vaultoroBalance.getCurrencyCode()), vaultoroBalance.getCash());
  }

  public static OpenOrders adaptVaultoroOpenOrders(Map<String, List<VaultoroOpenOrder>> orders) {

    List<LimitOrder> openOrders = new ArrayList<>();

    if (orders.containsKey("b")) {
      for (VaultoroOpenOrder o : orders.get("b")) {
        openOrders.add(adaptVaultoroOrder(o, OrderType.BID));
      }
    }

    if (orders.containsKey("s")) {
      for (VaultoroOpenOrder o : orders.get("s")) {
        openOrders.add(adaptVaultoroOrder(o, OrderType.ASK));
      }
    }

    return new OpenOrders(openOrders);
  }

  public static LimitOrder adaptVaultoroOrder(VaultoroOpenOrder o, OrderType orderType) {

    return new LimitOrder.Builder(orderType, new CurrencyPair("GLD", "BTC")).id(o.getOrderID()).limitPrice(o.getGoldPrice())
        .tradableAmount(o.getGoldAmount()).build();
  }

}
