package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.bitmarket.dto.account.BitMarketBalance;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrder;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.meta.MarketMetaData;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class BitMarketCompareUtils {

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

  public static void compareOrdersWithoutTimestamp(LimitOrder o1, LimitOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getCurrencyPair()).isEqualTo(o2.getCurrencyPair());
    assertThat(o1.getLimitPrice()).isEqualTo(o2.getLimitPrice());
    assertThat(o1.getTradableAmount()).isEqualTo(o2.getTradableAmount());
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

  public static void compareOrderBooks(OrderBook o1, OrderBook o2) {

    assertThat(o1.getTimeStamp()).isEqualTo(o2.getTimeStamp());

    compareOrderLists(o1.getAsks(), o2.getAsks());
    compareOrderLists(o1.getBids(), o2.getBids());
  }

  public static void compareOrderLists(List<LimitOrder> o1, List<LimitOrder> o2) {

    assertThat(o1.size()).isEqualTo(o2.size());
    for (int i=0; i< o1.size(); i++) {
      compareOrdersWithoutTimestamp(o1.get(i), o2.get(i));
    }
  }

  public static void compareBitMarketOrders(BitMarketOrder o1, BitMarketOrder o2) {
    assertThat(o1.getId()).isEqualTo(o2.getId());
    assertThat(o1.getMarket()).isEqualTo(o2.getMarket());
    assertThat(o1.getAmount()).isEqualTo(o2.getAmount());
    assertThat(o1.getRate()).isEqualTo(o2.getRate());
    assertThat(o1.getFiat()).isEqualTo(o2.getFiat());
    assertThat(o1.getType()).isEqualTo(o2.getType());
    assertThat(o1.getTime()).isEqualTo(o2.getTime());
  }

  public static void compareBitMarketOrderBooks(BitMarketOrderBook o1, BitMarketOrderBook o2) {
    compareQuoteMatrices(o1.getAsks(), o2.getAsks());
    compareQuoteMatrices(o1.getBids(), o2.getBids());

    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void compareBitMarketBitMarketTickers(BitMarketTicker o1, BitMarketTicker o2) {
    assertThat(o1.getAsk()).isEqualTo(o2.getAsk());
    assertThat(o1.getBid()).isEqualTo(o2.getBid());
    assertThat(o1.getLast()).isEqualTo(o2.getLast());
    assertThat(o1.getLow()).isEqualTo(o2.getLow());
    assertThat(o1.getHigh()).isEqualTo(o2.getHigh());
    assertThat(o1.getVwap()).isEqualTo(o2.getVwap());
    assertThat(o1.getVolume()).isEqualTo(o2.getVolume());

    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void compareBitMarketTrades(BitMarketTrade o1, BitMarketTrade o2) {

    assertThat(o1.getTid()).isEqualTo(o2.getTid());
    assertThat(o1.getPrice()).isEqualTo(o2.getPrice());
    assertThat(o1.getAmount()).isEqualTo(o2.getAmount());
    assertThat(o1.getDate()).isEqualTo(o2.getDate());
    assertThat(o1.toString()).isEqualTo(o2.toString());
  }

  public static void compareBitMarketBalances(BitMarketBalance o1, BitMarketBalance o2) {
    compareQuoteMaps(o1.getAvailable(), o2.getAvailable());
    compareQuoteMaps(o1.getBlocked(), o2.getBlocked());
  }

  private static void compareQuoteMaps(Map<String,BigDecimal> o1, Map<String,BigDecimal> o2) {

    assertThat(o1.size()).isEqualTo(o2.size());

    for (String key : o1.keySet()) {
      assertThat(o1.get(key)).isEqualTo(o2.get(key));
    }
  }

  private static void compareQuoteMatrices(BigDecimal[][] o1, BigDecimal[][] o2) {

    assertThat(o1.length).isEqualTo(o2.length);

    for (int i=0; i<o1.length; i++) {
      assertThat(o1[i].length).isEqualTo(o2[i].length);

      for (int j=0; j<o1[i].length; j++) {
        assertThat(o1[i][j]).isEqualTo(o2[i][j]);
      }
    }
  }
}
