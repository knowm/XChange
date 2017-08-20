package org.knowm.xchange.btcmarkets.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;

public class BTCMarketsAdaptersTest extends BTCMarketsDtoTestSupport {

  @Test
  public void shouldAdaptBalances() throws IOException {
    final BTCMarketsBalance[] response = parse(BTCMarketsBalance[].class);

    Wallet wallet = BTCMarketsAdapters.adaptWallet(Arrays.asList(response));

    assertThat(wallet.getBalances()).hasSize(3);
    assertThat(wallet.getBalance(Currency.LTC).getTotal()).isEqualTo(new BigDecimal("10.00000000"));
    assertThat(wallet.getBalance(Currency.LTC).getAvailable()).isEqualTo(new BigDecimal("10.00000000"));
  }

  @Test
  public void shoudAdaptOrderBook() throws IOException {
    final BTCMarketsOrderBook response = parse(BTCMarketsOrderBook.class);

    final OrderBook orderBook = BTCMarketsAdapters.adaptOrderBook(response, CurrencyPair.BTC_AUD);

    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1442997827000L);
    assertThat(orderBook.getAsks()).hasSize(135);
    assertThat(orderBook.getAsks().get(2).getLimitPrice()).isEqualTo(new BigDecimal("329.41"));
    assertThat(orderBook.getAsks().get(2).getTradableAmount()).isEqualTo(new BigDecimal("10.0"));
    assertThat(orderBook.getBids()).hasSize(94);
  }

  @Test
  public void shouldAdaptOrders() throws IOException {
    final BTCMarketsOrders response = parse(BTCMarketsOrders.class);

    final OpenOrders openOrders = BTCMarketsAdapters.adaptOpenOrders(response);

    assertThat(openOrders.getOpenOrders()).hasSize(2);
    assertThat(openOrders.getOpenOrders().get(1).getId()).isEqualTo("4345675");
    assertThat(openOrders.getOpenOrders().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(openOrders.getOpenOrders().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(openOrders.getOpenOrders().get(1).getTimestamp().getTime()).isEqualTo(1378636912705L);
    assertThat(openOrders.getOpenOrders().get(1).getLimitPrice()).isEqualTo("130.00000000");
    assertThat(openOrders.getOpenOrders().get(1).getTradableAmount()).isEqualTo("0.10000000");
  }

  @Test
  public void shouldAdaptTicker() throws IOException {
    final BTCMarketsTicker response = parse(BTCMarketsTicker.class);

    final Ticker ticker = BTCMarketsAdapters.adaptTicker(CurrencyPair.BTC_AUD, response);

    assertThat(ticker.getBid()).isEqualTo("137.00000000");
    assertThat(ticker.getAsk()).isEqualTo("140.00000000");
    assertThat(ticker.getLast()).isEqualTo("140.00000000");
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1378878117000L);
  }

  @Test
  public void shouldAdaptTradeHistory() throws IOException {
    final BTCMarketsTradeHistory response = parse(BTCMarketsTradeHistory.class);

    final List<UserTrade> userTrades = BTCMarketsAdapters.adaptTradeHistory(response.getTrades(), CurrencyPair.BTC_AUD).getUserTrades();
    assertThat(userTrades).hasSize(3);
    assertThat(userTrades.get(2).getId()).isEqualTo("45118157");
    assertThat(userTrades.get(2).getTimestamp().getTime()).isEqualTo(1442994673684L);
    assertThat(userTrades.get(2).getPrice()).isEqualTo("330.00000000");
    assertThat(userTrades.get(2).getTradableAmount()).isEqualTo("0.00100000");
    assertThat(userTrades.get(2).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(userTrades.get(2).getFeeAmount()).isEqualTo("0.00280499");
    assertThat(userTrades.get(2).getFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(userTrades.get(2).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(userTrades.get(1).getType()).isEqualTo(Order.OrderType.ASK);
  }
}