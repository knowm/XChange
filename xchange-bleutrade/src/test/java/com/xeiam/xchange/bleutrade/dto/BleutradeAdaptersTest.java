package com.xeiam.xchange.bleutrade.dto;

import com.xeiam.xchange.bleutrade.BleutradeAdapters;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.trade.OpenOrders;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TimeZone;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeAdaptersTest extends BleutradeDtoTestSupport {

  @Test
  public void shouldAdaptBalances() throws Exception {
    // given
    final BleutradeBalancesReturn response = parse(BleutradeBalancesReturn.class);

    // when
    Wallet wallet = BleutradeAdapters.adaptBleutradeBalances(response.getResult());

    // then
    assertThat(wallet.getBalances()).hasSize(2);
    assertThat(wallet.getBalance(Currency.DOGE).getTotal()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(wallet.getBalance(Currency.DOGE).getAvailable()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(wallet.getBalance(Currency.DOGE).getFrozen()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(wallet.getBalance(Currency.BTC).getTotal()).isEqualTo(new BigDecimal("15.49843675"));
    assertThat(wallet.getBalance(Currency.BTC).getAvailable()).isEqualTo(new BigDecimal("13.98901996"));
    assertThat(wallet.getBalance(Currency.BTC).getFrozen()).isEqualTo(new BigDecimal("0E-8"));
  }

  @Test
  public void shouldAdaptMarkets() throws Exception {
    // given
    final BleutradeMarketsReturn response = parse(BleutradeMarketsReturn.class);

    // when
    Set<CurrencyPair> currencyPairs = BleutradeAdapters.adaptBleutradeCurrencyPairs(response);

    // then
    assertThat(currencyPairs).hasSize(2);
    assertThat(currencyPairs).contains(CurrencyPair.DOGE_BTC, new CurrencyPair("BLEU", "BTC"));
  }

  @Test
  public void shouldAdaptMarketHistory() throws Exception {
    // given
    final BleutradeMarketHistoryReturn response = parse(BleutradeMarketHistoryReturn.class);

    long expectedTime1;
    long expectedTime2;

    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.MILLISECOND, 0);

    calendar.set(2014, Calendar.JULY, 29, 18, 8, 0);
    expectedTime1 = calendar.getTimeInMillis();
    calendar.set(2014, Calendar.JULY, 29, 18, 12, 35);
    expectedTime2 = calendar.getTimeInMillis();

    // when
    Trades trades = BleutradeAdapters.adaptBleutradeMarketHistory(response.getResult(), CurrencyPair.BTC_AUD);

    // then
    assertThat(trades.getlastID()).isEqualTo(0);
    assertThat(trades.getTrades()).hasSize(2);
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(trades.getTrades().get(0).getTimestamp().getTime()).isEqualTo(expectedTime1);
    assertThat(trades.getTrades().get(0).getTradableAmount()).isEqualTo(new BigDecimal("654971.69417461"));
    assertThat(trades.getTrades().get(0).getPrice()).isEqualTo(new BigDecimal("0.00000055"));
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(trades.getTrades().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(trades.getTrades().get(1).getTimestamp().getTime()).isEqualTo(expectedTime2);
    assertThat(trades.getTrades().get(1).getTradableAmount()).isEqualTo(new BigDecimal("120.00000000"));
    assertThat(trades.getTrades().get(1).getPrice()).isEqualTo(new BigDecimal("0.00006600"));
    assertThat(trades.getTrades().get(1).getType()).isEqualTo(Order.OrderType.ASK);
  }

  @Test
  public void shouldAdaptOpenOrders() throws Exception {
    // given
    final BleutradeOpenOrdersReturn response = parse(BleutradeOpenOrdersReturn.class);

    // when
    OpenOrders openOrders = BleutradeAdapters.adaptBleutradeOpenOrders(response.getResult());

    // then
    assertThat(openOrders.getOpenOrders()).hasSize(2);
    assertThat(openOrders.getOpenOrders().get(0).getId()).isEqualTo("65489");
    assertThat(openOrders.getOpenOrders().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(openOrders.getOpenOrders().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(openOrders.getOpenOrders().get(0).getLimitPrice()).isEqualTo(new BigDecimal("0.01268311"));
    assertThat(openOrders.getOpenOrders().get(0).getTradableAmount()).isEqualTo(new BigDecimal("5.00000000"));
    assertThat(openOrders.getOpenOrders().get(0).getTimestamp()).isNull();  // 'created' to 'timestamp' convertation is probably missed
    assertThat(openOrders.getOpenOrders().get(1).getId()).isEqualTo("65724");
    assertThat(openOrders.getOpenOrders().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(openOrders.getOpenOrders().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.DOGE_BTC);
    assertThat(openOrders.getOpenOrders().get(1).getLimitPrice()).isEqualTo(new BigDecimal("0.00000055"));
    assertThat(openOrders.getOpenOrders().get(1).getTradableAmount()).isEqualTo(new BigDecimal("795.00000000"));
    assertThat(openOrders.getOpenOrders().get(1).getTimestamp()).isNull();  // 'created' to 'timestamp' convertation is probably missed
  }

  @Test
  public void shouldAdaptOrderBook() throws Exception {
    // given
    final BleutradeOrderBookReturn response = parse(BleutradeOrderBookReturn.class);

    // when
    OrderBook orderBook = BleutradeAdapters.adaptBleutradeOrderBook(response.getResult(), CurrencyPair.BTC_AUD);

    // then
    assertThat(orderBook.getAsks()).hasSize(4);

    assertThat(orderBook.getBids()).hasSize(2);

    assertThat(orderBook.getBids().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(orderBook.getBids().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getBids().get(0).getTradableAmount()).isEqualTo(new BigDecimal("4.99400000"));
    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("3.00650900"));
    assertThat(orderBook.getBids().get(1).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(orderBook.getBids().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getBids().get(1).getTradableAmount()).isEqualTo(new BigDecimal("50.00000000"));
    assertThat(orderBook.getBids().get(1).getLimitPrice()).isEqualTo(new BigDecimal("3.50000000"));

    assertThat(orderBook.getAsks().get(0).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orderBook.getAsks().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getAsks().get(0).getTradableAmount()).isEqualTo(new BigDecimal("12.44147454"));
    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("5.13540000"));
    assertThat(orderBook.getAsks().get(1).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orderBook.getAsks().get(1).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getAsks().get(1).getTradableAmount()).isEqualTo(new BigDecimal("100.00000000"));
    assertThat(orderBook.getAsks().get(1).getLimitPrice()).isEqualTo(new BigDecimal("6.25500000"));
    assertThat(orderBook.getAsks().get(2).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orderBook.getAsks().get(2).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getAsks().get(2).getTradableAmount()).isEqualTo(new BigDecimal("30.00000000"));
    assertThat(orderBook.getAsks().get(2).getLimitPrice()).isEqualTo(new BigDecimal("6.75500001"));
    assertThat(orderBook.getAsks().get(3).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(orderBook.getAsks().get(3).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(orderBook.getAsks().get(3).getTradableAmount()).isEqualTo(new BigDecimal("13.49989999"));
    assertThat(orderBook.getAsks().get(3).getLimitPrice()).isEqualTo(new BigDecimal("6.76260099"));
  }

  @Test
  public void shouldAdaptTicker() throws Exception {
    // given
    final BleutradeTickerReturn response = parse(BleutradeTickerReturn.class);
    GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.MILLISECOND, 0);

    calendar.set(2014, Calendar.JULY, 29, 11, 19, 30);
    long expectedTime = calendar.getTimeInMillis();

    // when
    Ticker ticker = BleutradeAdapters.adaptBleutradeTicker(response.getResult().get(0));

    // then
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("0.00100000"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("0.00101977"));
    assertThat(ticker.getCurrencyPair()).isEqualTo(new CurrencyPair("BLEU", "BTC"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("0.00105000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("0.00101977"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("0.00086000"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(expectedTime);
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("2450.97496015"));
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("0.00103455"));
  }

  @Test
  public void shouldAdaptExchangeMetaData() throws Exception {
    // given
    final BleutradeCurrenciesReturn currenciesResponse = parse(BleutradeCurrenciesReturn.class);
    final BleutradeMarketsReturn marketsResponse = parse(BleutradeMarketsReturn.class);

    // when
    ExchangeMetaData exchangeMetaData = BleutradeAdapters.adaptToExchangeMetaData(currenciesResponse.getResult(), marketsResponse.getResult());

    // then
    assertThat(exchangeMetaData.getCurrencyMetaDataMap()).hasSize(2);
    assertThat(exchangeMetaData.getCurrencyMetaDataMap().get(Currency.BTC).scale).isEqualTo(8);
    assertThat(exchangeMetaData.getCurrencyMetaDataMap().get(Currency.LTC).scale).isEqualTo(8);
    assertThat(exchangeMetaData.getMarketMetaDataMap()).hasSize(2);

    // there is no reliable information about valid tradingFee calculation formula
    assertThat(exchangeMetaData.getMarketMetaDataMap().get(CurrencyPair.DOGE_BTC).toString()).isEqualTo("MarketMetaData{tradingFee=0.00499375, minimumAmount=0.10000000, priceScale=8}");
    assertThat(exchangeMetaData.getMarketMetaDataMap().get(new CurrencyPair("BLEU", "BTC")).toString()).isEqualTo("MarketMetaData{tradingFee=0.00499375, minimumAmount=1E-8, priceScale=8}");
  }

}
