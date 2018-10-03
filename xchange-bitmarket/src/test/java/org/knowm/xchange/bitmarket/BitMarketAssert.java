package org.knowm.xchange.bitmarket;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bitmarket.dto.account.BitMarketBalance;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import org.knowm.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import org.knowm.xchange.bitmarket.dto.trade.BitMarketOrder;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public class BitMarketAssert {

  public static void assertEquals(Balance o1, Balance o2) {
    assertThat(o1.getCurrency()).isEqualTo(o2.getCurrency());
    assertThat(o1.getTotal()).isEqualTo(o2.getTotal());
    assertThat(o1.getAvailable()).isEqualTo(o2.getAvailable());
    assertThat(o1.getFrozen()).isEqualTo(o2.getFrozen());
  }

  public static void assertEquals(Trade o1, Trade o2) {
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getOriginalAmount()).isEqualTo(o2.getOriginalAmount());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.getId()).isEqualTo(o2.getId());
  }

  public static void assertEquals(UserTrade o1, UserTrade o2) {
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getOriginalAmount()).isEqualTo(o2.getOriginalAmount());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
    assertThat(o1.getId()).isEqualTo(o2.getId());

    assertThat(o1.getOrderId()).isEqualTo(o2.getOrderId());
    assertThat(o1.getFeeAmount()).isEqualTo(o2.getFeeAmount());
    assertThat(o1.getFeeCurrency()).isEqualTo(o2.getFeeCurrency());
  }

  public static void assertEquals(LimitOrder o1, LimitOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getLimitPrice()).isEqualTo(o2.getLimitPrice());
    assertThat(o1.getOriginalAmount()).isEqualTo(o2.getOriginalAmount());
    assertThat(o1.getTimestamp()).isEqualTo(o2.getTimestamp());
  }

  public static void assertEqualsWithoutTimestamp(LimitOrder o1, LimitOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getLimitPrice()).isEqualTo(o2.getLimitPrice());
    assertThat(o1.getOriginalAmount()).isEqualTo(o2.getOriginalAmount());
  }

  public static void assertEquals(Ticker o1, Ticker o2) {
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

  public static void assertEquals(OrderBook o1, OrderBook o2) {

    assertThat(o1.getTimeStamp()).isEqualTo(o2.getTimeStamp());

    assertEquals(o1.getAsks(), o2.getAsks());
    assertEquals(o1.getBids(), o2.getBids());
  }

  public static void assertEquals(List<LimitOrder> o1, List<LimitOrder> o2) {

    assertThat(o1.size()).isEqualTo(o2.size());
    for (int i = 0; i < o1.size(); i++) {
      assertEqualsWithoutTimestamp(o1.get(i), o2.get(i));
    }
  }

  public static void assertEquals(BitMarketOrder o1, BitMarketOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getMarket()).isEqualTo(o2.getMarket());
    assertThat(o1.getAmount()).isEqualTo(o2.getAmount());
    assertThat(o1.getRate()).isEqualTo(o2.getRate());
    assertThat(o1.getFiat()).isEqualTo(o2.getFiat());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getTime()).isEqualTo(o2.getTime());
  }

  public static void assertEquals(BitMarketOrderBook o1, BitMarketOrderBook o2) {
    assertEquals(o1.getAsks(), o2.getAsks());
    assertEquals(o1.getBids(), o2.getBids());

    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void assertEquals(BitMarketTicker o1, BitMarketTicker o2) {
    assertThat(o1.getAsk()).isEqualTo(o2.getAsk());
    assertThat(o1.getBid()).isEqualTo(o2.getBid());
    assertThat(o1.getLast()).isEqualTo(o2.getLast());
    assertThat(o1.getLow()).isEqualTo(o2.getLow());
    assertThat(o1.getHigh()).isEqualTo(o2.getHigh());
    assertThat(o1.getVwap()).isEqualTo(o2.getVwap());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());

    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void assertEquals(BitMarketTrade o1, BitMarketTrade o2) {

    assertThat(o1.getTid()).isEqualTo(o2.getTid());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getAmount()).isEqualTo(o2.getAmount());
    assertThat(o1.getDate()).isEqualTo(o2.getDate());
    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void assertEquals(BitMarketBalance o1, BitMarketBalance o2) {
    assertEquals(o1.getAvailable(), o2.getAvailable());
    assertEquals(o1.getBlocked(), o2.getBlocked());
  }

  private static void assertEquals(Map<String, BigDecimal> o1, Map<String, BigDecimal> o2) {

    assertThat(o1.size()).isEqualTo(o2.size());

    for (String key : o1.keySet()) {
      assertThat(o1.get(key)).isEqualTo(o2.get(key));
    }
  }

  private static void assertEquals(BigDecimal[][] o1, BigDecimal[][] o2) {

    assertThat(o1.length).isEqualTo(o2.length);

    for (int i = 0; i < o1.length; i++) {
      assertThat(o1[i].length).isEqualTo(o2[i].length);

      for (int j = 0; j < o1[i].length; j++) {
        assertThat(o1[i][j]).isEqualTo(o2[i][j]);
      }
    }
  }
}
