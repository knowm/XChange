package com.xeiam.xchange.vaultofsatoshi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosAccount;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosWallet;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosOrder;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTicker;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;
import com.xeiam.xchange.vaultofsatoshi.dto.trade.VosTradeOrder;

/**
 * Various adapters for converting from VaultOfSatoshi DTOs to XChange DTOs
 */
public final class VaultOfSatoshiAdapters {

  /**
   * private Constructor
   */
  private VaultOfSatoshiAdapters() {

  }

  /**
   * Adapts a vosOrder to a LimitOrder
   *
   * @param amount
   * @param price
   * @param pair
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(VosOrder vosOrder, CurrencyPair pair, String orderTypeString, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, vosOrder.getAmount().getValue(), pair, id, null, vosOrder.getPrice().getValue());
  }

  /**
   * Adapts a List of vosOrders to a List of LimitOrders
   *
   * @param vosOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(List<VosOrder> vosOrders, CurrencyPair pair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    for (VosOrder vosOrder : vosOrders) {
      limitOrders.add(adaptOrder(vosOrder, pair, orderType, id));
    }

    return limitOrders;
  }

  /**
   * Adapts a VaultOfSatoshiTrade to a Trade Object
   *
   * @param vosTrade A VaultOfSatoshi trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(VosTrade vosTrade, CurrencyPair currencyPair) {

    BigDecimal amount = vosTrade.getUnitsTraded().getValue();
    Date date = DateUtils.fromMillisUtc(vosTrade.getTimestamp() / 1000L);
    final String tradeId = String.valueOf(vosTrade.getTransactionId());

    return new Trade(null, amount, currencyPair, vosTrade.getPrice().getValue(), date, tradeId);
  }

  /**
   * Adapts a VosTrade[] to a Trades Object
   *
   * @param vosTrades The VosTrades trade data
   * @return The trades
   */
  public static Trades adaptTrades(VosTrade[] vosTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (VosTrade vosTrade : vosTrades) {
      long tradeId = vosTrade.getTransactionId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(vosTrade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a VaultOfSatoshiTicker to a Ticker Object
   *
   * @param vosTicker
   * @return
   */
  public static Ticker adaptTicker(VosTicker vosTicker, CurrencyPair currencyPair) {

    BigDecimal last = vosTicker.getLast();
    BigDecimal high = vosTicker.getHigh();
    BigDecimal low = vosTicker.getLow();
    BigDecimal volume = vosTicker.getVolume();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withVolume(volume).build();
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static AccountInfo adaptAccountInfo(VosAccount account, String userName) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (Entry<String, VosWallet> entry : account.getWallets().getVosMap().entrySet()) {
      wallets.add(new Wallet(entry.getKey(), entry.getValue().getBalance().getValue()));
    }

    return new AccountInfo("" + account.getAccount_id(), account.getTrade_fee().getVosMap().get("BTC").getValue(), wallets);
  }

  public static Trades adaptTradeHistory(VosTradeOrder[] vosUserTransactions) {

    List<Trade> trades = new ArrayList<Trade>();
    for (VosTradeOrder order : vosUserTransactions) {

      OrderType orderType = order.getType() == "bid" ? OrderType.BID : OrderType.ASK;
      CurrencyPair currPair = new CurrencyPair(order.getOrder_currency(), order.getPayment_currency());

      trades.add(new Trade(orderType, order.getUnits().getValue(), currPair, order.getPrice().getValue(), DateUtils.fromMillisUtc(order.getDate_completed() / 1000L), String.valueOf(order
          .getOrder_id())));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

}
