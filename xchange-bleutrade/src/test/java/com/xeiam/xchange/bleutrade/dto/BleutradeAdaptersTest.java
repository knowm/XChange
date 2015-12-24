package com.xeiam.xchange.bleutrade.dto;

import com.xeiam.xchange.bleutrade.BleutradeAdapters;
import com.xeiam.xchange.bleutrade.BleutradeAssert;
import com.xeiam.xchange.bleutrade.BleutradeTestData;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrenciesReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketHistoryReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarketsReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBookReturn;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTickerReturn;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrdersReturn;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeAdaptersTest extends BleutradeDtoTestSupport implements BleutradeTestData {

  @Test
  public void shouldAdaptBalances() throws IOException {
    // given
    final BleutradeBalancesReturn response = parse(BleutradeBalancesReturn.class);

    // when
    Wallet wallet = BleutradeAdapters.adaptBleutradeBalances(response.getResult());

    // then
    assertThat(wallet.getBalances()).hasSize(2);

    BleutradeAssert.assertEquals(wallet.getBalance(Currency.DOGE), BALANCES[0]);
    BleutradeAssert.assertEquals(wallet.getBalance(Currency.BTC), BALANCES[1]);
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
      BleutradeAssert.assertEquals(tradeList.get(i), TRADES[i]);
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
      BleutradeAssert.assertEquals(orderList.get(i), ORDERS[i]);
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
      BleutradeAssert.assertEquals(bids.get(i), BIDS[i]);
    }

    List<LimitOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(4);

    for (int i=0; i<asks.size(); i++) {
      BleutradeAssert.assertEquals(asks.get(i), ASKS[i]);
    }
  }

  @Test
  public void shouldAdaptTicker() throws IOException {
    // given
    final BleutradeTickerReturn response = parse(BleutradeTickerReturn.class);

    // when
    Ticker ticker = BleutradeAdapters.adaptBleutradeTicker(response.getResult().get(0));

    // then
    BleutradeAssert.assertEquals(ticker, TICKER);
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
    BleutradeAssert.assertEquals(marketMetaDataMap.get(CurrencyPair.DOGE_BTC), META_DATA_LIST[0]);
    assertThat(marketMetaDataMap.get(CurrencyPair.DOGE_BTC).toString()).isEqualTo(META_DATA_STR[0]);

    BleutradeAssert.assertEquals(marketMetaDataMap.get(BLEU_BTC_CP), META_DATA_LIST[1]);
    assertThat(marketMetaDataMap.get(BLEU_BTC_CP).toString()).isEqualTo(META_DATA_STR[1]);
  }

}
