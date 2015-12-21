package com.xeiam.xchange.bleutrade;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.meta.MarketMetaData;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeCompareUtils {

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

  public static void compareOrders(LimitOrder o1, LimitOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getLimitPrice()).isEqualTo(o2.getLimitPrice());
    assertThat(o1.getTradableAmount()).isEqualTo(o2.getTradableAmount());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
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

  public static void compareMarketMetaData(MarketMetaData o1, MarketMetaData o2) {
    assertThat(o1.getMinimumAmount()).isEqualTo(o2.getMinimumAmount());
    assertThat(o1.getPriceScale()).isEqualTo(o2.getPriceScale());
    assertThat(o1.getTradingFee()).isEqualTo(o2.getTradingFee());
  }
}
