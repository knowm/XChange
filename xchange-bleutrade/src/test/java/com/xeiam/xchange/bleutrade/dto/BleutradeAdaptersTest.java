package com.xeiam.xchange.bleutrade.dto;

import com.xeiam.xchange.bleutrade.BleutradeAdapters;
import com.xeiam.xchange.bleutrade.BleutradeCompareUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeAdaptersTest extends BleutradeDtoTestSupport {

  private static final Balance[] BALANCES = new Balance[] {
      new Balance(Currency.DOGE, new BigDecimal("0E-8"), new BigDecimal("0E-8"), new BigDecimal("0E-8")),
      new Balance(Currency.BTC, new BigDecimal("15.49843675"), new BigDecimal("13.98901996"), new BigDecimal("0E-8")),
  };

  private static final Trade[] TRADES = new Trade[] {
      new Trade(Order.OrderType.BID, new BigDecimal("654971.69417461"), CurrencyPair.BTC_AUD,
          new BigDecimal("0.00000055"), new Date(1406657280000L), null),
      new Trade(Order.OrderType.ASK, new BigDecimal("120.00000000"), CurrencyPair.BTC_AUD,
          new BigDecimal("0.00006600"), new Date(1406657555000L), null),
  };

  private static final LimitOrder[] ORDER = new LimitOrder[] {   // timestampas are always null: 'created' to 'timestamp' convertation is probably missed
      new LimitOrder(Order.OrderType.BID, new BigDecimal("5.00000000"), CurrencyPair.LTC_BTC, "65489", null, new BigDecimal("0.01268311")),
      new LimitOrder(Order.OrderType.ASK, new BigDecimal("795.00000000"), CurrencyPair.DOGE_BTC, "65724", null, new BigDecimal("0.00000055")),
  };

  private static final LimitOrder[] BIDS = new LimitOrder[] {
      new LimitOrder(Order.OrderType.BID, new BigDecimal("4.99400000"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("3.00650900")),
      new LimitOrder(Order.OrderType.BID, new BigDecimal("50.00000000"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("3.50000000"))
  };

  private static final LimitOrder[] ASKS = new LimitOrder[] {
      new LimitOrder(Order.OrderType.ASK, new BigDecimal("12.44147454"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("5.13540000")),
      new LimitOrder(Order.OrderType.ASK, new BigDecimal("100.00000000"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("6.25500000")),
      new LimitOrder(Order.OrderType.ASK, new BigDecimal("30.00000000"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("6.75500001")),
      new LimitOrder(Order.OrderType.ASK, new BigDecimal("13.49989999"), CurrencyPair.BTC_AUD, null, null, new BigDecimal("6.76260099"))
  };

  private static final Ticker TICKER = new Ticker.Builder()
      .currencyPair(BLEU_BTC_CP).last(new BigDecimal("0.00101977")).bid(new BigDecimal("0.00100000"))
      .ask(new BigDecimal("0.00101977")).high(new BigDecimal("0.00105000")).low(new BigDecimal("0.00086000"))
      .vwap(new BigDecimal("0.00103455")).volume(new BigDecimal("2450.97496015")).timestamp(new Date(1406632770000L)).build();

  private static final MarketMetaData[] META_DATA_LIST = new MarketMetaData[] {
      new MarketMetaData(new BigDecimal("0.00499375"), new BigDecimal("0.10000000"), 8),
      new MarketMetaData(new BigDecimal("0.00499375"), new BigDecimal("0.00000001"), 8)
  };

  private static final String[] META_DATA_STR = new String[] {
      "MarketMetaData{tradingFee=0.00499375, minimumAmount=0.10000000, priceScale=8}",
      "MarketMetaData{tradingFee=0.00499375, minimumAmount=1E-8, priceScale=8}"
  };

  @Test
  public void shouldAdaptBalances() throws IOException {
    // given
    final BleutradeBalancesReturn response = parse(BleutradeBalancesReturn.class);

    // when
    Wallet wallet = BleutradeAdapters.adaptBleutradeBalances(response.getResult());

    // then
    assertThat(wallet.getBalances()).hasSize(2);

    BleutradeCompareUtils.compareBalances(wallet.getBalance(Currency.DOGE), BALANCES[0]);
    BleutradeCompareUtils.compareBalances(wallet.getBalance(Currency.BTC), BALANCES[1]);
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

    // when
    Trades trades = BleutradeAdapters.adaptBleutradeMarketHistory(response.getResult(), CurrencyPair.BTC_AUD);

    // then
    assertThat(trades.getlastID()).isEqualTo(0);

    List<Trade> tradeList = trades.getTrades();
    assertThat(tradeList).hasSize(2);

    for (int i=0; i<tradeList.size(); i++) {
      BleutradeCompareUtils.compareTrades(tradeList.get(i), TRADES[i]);
    }
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

    for (int i=0; i<orderList.size(); i++) {
      BleutradeCompareUtils.compareOrders(orderList.get(i), ORDER[i]);
    }
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

    for (int i=0; i<bids.size(); i++) {
      BleutradeCompareUtils.compareOrders(bids.get(i), BIDS[i]);
    }

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(4);

    for (int i=0; i<asks.size(); i++) {
      BleutradeCompareUtils.compareOrders(asks.get(i), ASKS[i]);
    }
  }

  @Test
  public void shouldAdaptTicker() throws IOException {
    // given
    final BleutradeTickerReturn response = parse(BleutradeTickerReturn.class);

    // when
    Ticker ticker = BleutradeAdapters.adaptBleutradeTicker(response.getResult().get(0));

    // then
    BleutradeCompareUtils.compareTickers(ticker, TICKER);
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
    BleutradeCompareUtils.compareMarketMetaData(marketMetaDataMap.get(CurrencyPair.DOGE_BTC), META_DATA_LIST[0]);
    assertThat(marketMetaDataMap.get(CurrencyPair.DOGE_BTC).toString()).isEqualTo(META_DATA_STR[0]);

    BleutradeCompareUtils.compareMarketMetaData(marketMetaDataMap.get(BLEU_BTC_CP), META_DATA_LIST[1]);
    assertThat(marketMetaDataMap.get(BLEU_BTC_CP).toString()).isEqualTo(META_DATA_STR[1]);
  }

}
