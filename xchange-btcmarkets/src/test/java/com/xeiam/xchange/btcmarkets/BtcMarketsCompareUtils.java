package com.xeiam.xchange.btcmarkets;

import com.xeiam.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import com.xeiam.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;

public class BtcMarketsCompareUtils {

  public static void compareBalances(Balance o1, Balance o2) {
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getTotal()).isEqualTo(o2.getTotal());
    assertThat(o1.getAvailable()).isEqualTo(o2.getAvailable());
    assertThat(o1.getFrozen()).isEqualTo(o2.getFrozen());
  }

  public static void compareTrades(Trade o1, Trade o2) {
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getTradableAmount()).isEqualTo(o2.getTradableAmount());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.getId()).isEqualTo(o2.getId());
  }

  public static void compareUserTrades(UserTrade o1, UserTrade o2) {
    compareTrades(o1, o2);

    assertThat(o1.getOrderId()).isEqualTo(o2.getOrderId());
    assertThat(o1.getFeeAmount()).isEqualTo(o2.getFeeAmount());
    assertThat(o1.getFeeCurrency()).isEqualTo(o2.getFeeCurrency());
  }

  public static void compareOrders(LimitOrder o1, LimitOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getLimitPrice()).isEqualTo(o2.getLimitPrice());
    assertThat(o1.getTradableAmount()).isEqualTo(o2.getTradableAmount());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
  }

  public static void compareOrderHistories(LimitOrder o1, Order.OrderType orderType,
                                           CurrencyPair currencyPair, BigDecimal[] history) {
    assertThat(o1.getType()).isEqualTo(orderType);
    assertThat(o1.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(o1.getLimitPrice()).isEqualTo(history[0]);
    assertThat(o1.getTradableAmount()).isEqualTo(history[1]);
  }

  public static void compareTickers(Ticker o1, Ticker o2) {
    assertThat(o1.getBid()).isEqualTo(o2.getBid());
    assertThat(o1.getAsk()).isEqualTo(o2.getAsk());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getHigh()).isEqualTo(o2.getHigh());
    assertThat(o1.getLast()).isEqualTo(o2.getLast());
    assertThat(o1.getLow()).isEqualTo(o2.getLow());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());
    assertThat(o1.getVwap()).isEqualTo(o2.getVwap());
  }

  public static void compareBTCMarketsBalances(BTCMarketsBalance o1, BTCMarketsBalance o2) {
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getBalance()).isEqualTo(o2.getBalance());
    assertThat(o1.getPendingFunds()).isEqualTo(o2.getPendingFunds());
    assertThat(o1.getAvailable()).isEqualTo(o2.getAvailable());
    assertThat(o1.toString()).isEqualTo(o2.toString());

  }

  public static void compareBTCMarketsOrders(BTCMarketsOrder o1, BTCMarketsOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getInstrument()).isEqualTo(o2.getInstrument());
    assertThat(o1.getOrderSide()).isEqualTo(o2.getOrderSide());
    assertThat(o1.getOrdertype()).isEqualTo(o2.getOrdertype());
    assertThat(o1.getCreationTime()).isEqualTo(o2.getCreationTime());
    assertThat(o1.getStatus()).isEqualTo(o2.getStatus());
    assertThat(o1.getErrorMessage()).isEqualTo(o2.getErrorMessage());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());
    assertThat(o1.getOpenVolume()).isEqualTo(o2.getOpenVolume());
    assertThat(o1.getClientRequestId()).isEqualTo(o2.getClientRequestId());
    assertThat(o1.getTrades().size()).isEqualTo(o2.getTrades().size());

    for (int i=0; i<o1.getTrades().size(); i++) {
      compareBtcMarketsUserTrades(o1.getTrades().get(i), o2.getTrades().get(i));
    }

    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void compareBtcMarketsUserTrades(
    BTCMarketsUserTrade o1, BTCMarketsUserTrade o2) {

    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getCreationTime()).isEqualTo(o2.getCreationTime());
    assertThat(o1.getDescription()).isEqualTo(o2.getDescription());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());
    assertThat(o1.getFee()).isEqualTo(o2.getFee());
    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void compareBtcMarketsTickers(
    BTCMarketsTicker o1, BTCMarketsTicker o2) {

    assertThat(o1.getBestBid()).isEqualTo(o2.getBestBid());
    assertThat(o1.getBestAsk()).isEqualTo(o2.getBestAsk());
    assertThat(o1.getLastPrice()).isEqualTo(o2.getLastPrice());
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getInstrument()).isEqualTo(o2.getInstrument());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.toString()).isEqualTo(o2.toString());
  }



}
