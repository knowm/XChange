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
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.meta.CurrencyMetaData;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.meta.MarketMetaData;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeAdaptersTest extends BleutradeDtoTestSupport {

  @Test
  public void shouldAdaptBalances() throws IOException {
    // given
    final BleutradeBalancesReturn response = parse(BleutradeBalancesReturn.class);

    // when
    Wallet wallet = BleutradeAdapters.adaptBleutradeBalances(response.getResult());

    // then
    assertThat(wallet.getBalances()).hasSize(2);

    Balance dogeBalance = wallet.getBalance(Currency.DOGE);
    assertThat(dogeBalance.getTotal()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(dogeBalance.getAvailable()).isEqualTo(new BigDecimal("0E-8"));
    assertThat(dogeBalance.getFrozen()).isEqualTo(new BigDecimal("0E-8"));

    Balance btcBalance = wallet.getBalance(Currency.BTC);
    assertThat(btcBalance.getTotal()).isEqualTo(new BigDecimal("15.49843675"));
    assertThat(btcBalance.getAvailable()).isEqualTo(new BigDecimal("13.98901996"));
    assertThat(btcBalance.getFrozen()).isEqualTo(new BigDecimal("0E-8"));
  }

  @Test
  public void shouldAdaptMarkets() throws IOException {
    // given
    final BleutradeMarketsReturn response = parse(BleutradeMarketsReturn.class);

    // when
    Set<CurrencyPair> currencyPairs = BleutradeAdapters.adaptBleutradeCurrencyPairs(response);

    // then
    assertThat(currencyPairs).hasSize(2);
    assertThat(currencyPairs).contains(CurrencyPair.DOGE_BTC, BLEU_BTC_CP);
  }

  @Test
  public void shouldAdaptMarketHistory() throws IOException {
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

    List<Trade> tradeList = trades.getTrades();
    assertThat(tradeList).hasSize(2);

    Trade trade1 = tradeList.get(0);
    assertThat(trade1.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(trade1.getTimestamp().getTime()).isEqualTo(expectedTime1);
    assertThat(trade1.getTradableAmount()).isEqualTo(new BigDecimal("654971.69417461"));
    assertThat(trade1.getPrice()).isEqualTo(new BigDecimal("0.00000055"));
    assertThat(trade1.getType()).isEqualTo(Order.OrderType.BID);

    Trade trade2 = tradeList.get(1);
    assertThat(trade2.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(trade2.getTimestamp().getTime()).isEqualTo(expectedTime2);
    assertThat(trade2.getTradableAmount()).isEqualTo(new BigDecimal("120.00000000"));
    assertThat(trade2.getPrice()).isEqualTo(new BigDecimal("0.00006600"));
    assertThat(trade2.getType()).isEqualTo(Order.OrderType.ASK);
  }

  @Test
  public void shouldAdaptOpenOrders() throws IOException {
    // given
    final BleutradeOpenOrdersReturn response = parse(BleutradeOpenOrdersReturn.class);

    // when
    OpenOrders openOrders = BleutradeAdapters.adaptBleutradeOpenOrders(response.getResult());

    // then
    List<LimitOrder> orderList = openOrders.getOpenOrders();
    assertThat(orderList).hasSize(2);

    LimitOrder order1 = orderList.get(0);
    assertThat(order1.getId()).isEqualTo("65489");
    assertThat(order1.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(order1.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(order1.getLimitPrice()).isEqualTo(new BigDecimal("0.01268311"));
    assertThat(order1.getTradableAmount()).isEqualTo(new BigDecimal("5.00000000"));
    assertThat(order1.getTimestamp()).isNull();  // 'created' to 'timestamp' convertation is probably missed

    LimitOrder order2 = orderList.get(1);
    assertThat(order2.getId()).isEqualTo("65724");
    assertThat(order2.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(order2.getCurrencyPair()).isEqualTo(CurrencyPair.DOGE_BTC);
    assertThat(order2.getLimitPrice()).isEqualTo(new BigDecimal("0.00000055"));
    assertThat(order2.getTradableAmount()).isEqualTo(new BigDecimal("795.00000000"));
    assertThat(order2.getTimestamp()).isNull();  // 'created' to 'timestamp' convertation is probably missed
  }

  @Test
  public void shouldAdaptOrderBook() throws IOException {
    // given
    final BleutradeOrderBookReturn response = parse(BleutradeOrderBookReturn.class);

    // when
    OrderBook orderBook = BleutradeAdapters.adaptBleutradeOrderBook(response.getResult(), CurrencyPair.BTC_AUD);

    // then
    List<LimitOrder> bids = orderBook.getBids();
    assertThat(bids).hasSize(2);

    LimitOrder bid1 = bids.get(0);
    assertThat(bid1.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(bid1.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(bid1.getTradableAmount()).isEqualTo(new BigDecimal("4.99400000"));
    assertThat(bid1.getLimitPrice()).isEqualTo(new BigDecimal("3.00650900"));

    LimitOrder bid2 = bids.get(1);
    assertThat(bid2.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(bid2.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(bid2.getTradableAmount()).isEqualTo(new BigDecimal("50.00000000"));
    assertThat(bid2.getLimitPrice()).isEqualTo(new BigDecimal("3.50000000"));

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(4);

    LimitOrder ask1 = asks.get(0);
    assertThat(ask1.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(ask1.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(ask1.getTradableAmount()).isEqualTo(new BigDecimal("12.44147454"));
    assertThat(ask1.getLimitPrice()).isEqualTo(new BigDecimal("5.13540000"));

    LimitOrder ask2 = asks.get(1);
    assertThat(ask2.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(ask2.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(ask2.getTradableAmount()).isEqualTo(new BigDecimal("100.00000000"));
    assertThat(ask2.getLimitPrice()).isEqualTo(new BigDecimal("6.25500000"));

    LimitOrder ask3 = asks.get(2);
    assertThat(ask3.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(ask3.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(ask3.getTradableAmount()).isEqualTo(new BigDecimal("30.00000000"));
    assertThat(ask3.getLimitPrice()).isEqualTo(new BigDecimal("6.75500001"));

    LimitOrder ask4 = asks.get(3);
    assertThat(ask4.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(ask4.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(ask4.getTradableAmount()).isEqualTo(new BigDecimal("13.49989999"));
    assertThat(ask4.getLimitPrice()).isEqualTo(new BigDecimal("6.76260099"));
  }

  @Test
  public void shouldAdaptTicker() throws IOException {
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
    assertThat(ticker.getCurrencyPair()).isEqualTo(BLEU_BTC_CP);
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("0.00105000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("0.00101977"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("0.00086000"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(expectedTime);
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("2450.97496015"));
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("0.00103455"));
  }

  @Test
  public void shouldAdaptExchangeMetaData() throws IOException {
    // given
    final BleutradeCurrenciesReturn currenciesResponse = parse(BleutradeCurrenciesReturn.class);
    final BleutradeMarketsReturn marketsResponse = parse(BleutradeMarketsReturn.class);

    // when
    ExchangeMetaData exchangeMetaData = BleutradeAdapters.adaptToExchangeMetaData(currenciesResponse.getResult(), marketsResponse.getResult());

    // then
    Map<Currency,CurrencyMetaData> currencyMetaDataMap = exchangeMetaData.getCurrencyMetaDataMap();
    assertThat(currencyMetaDataMap).hasSize(2);
    assertThat(currencyMetaDataMap.get(Currency.BTC).scale).isEqualTo(8);
    assertThat(currencyMetaDataMap.get(Currency.LTC).scale).isEqualTo(8);

    Map<CurrencyPair,MarketMetaData> marketMetaDataMap = exchangeMetaData.getMarketMetaDataMap();
    assertThat(marketMetaDataMap).hasSize(2);
    // there is no reliable information about valid tradingFee calculation formula
    assertThat(marketMetaDataMap.get(CurrencyPair.DOGE_BTC).toString()).isEqualTo("MarketMetaData{tradingFee=0.00499375, minimumAmount=0.10000000, priceScale=8}");
    assertThat(marketMetaDataMap.get(BLEU_BTC_CP).toString()).isEqualTo("MarketMetaData{tradingFee=0.00499375, minimumAmount=1E-8, priceScale=8}");
  }

}
