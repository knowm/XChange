package com.xeiam.xchange.kraken.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.account.results.KrakenBalanceResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenTickerResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult.KrakenTradeHistory;

public class KrakenAdaptersTest {

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTickerResult krakenTicker = mapper.readValue(is, KrakenTickerResult.class);
    CurrencyPair currencyPair = CurrencyPair.BTC_EUR;
    String krakenCurencyPair = "XXBTZEUR";
    Ticker ticker = KrakenAdapters.adaptTicker(krakenTicker.getResult().get(krakenCurencyPair), currencyPair);

    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("562.26651"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("560.46600"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("560.00000"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("591.11000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("560.87711"));
    assertThat(ticker.getVolume()).isEqualByComparingTo("600.91850325");
    assertThat(ticker.getCurrencyPair().baseSymbol).isEqualTo(currencyPair.baseSymbol);
  }

  @Test
  public void testAdaptCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/marketdata/example-assetpairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenAssetPairsResult krakenAssetPairs = mapper.readValue(is, KrakenAssetPairsResult.class);

    Set<CurrencyPair> pairs = KrakenAdapters.adaptCurrencyPairs(krakenAssetPairs.getResult().keySet());
    assertThat(pairs).hasSize(21);
    assertThat(pairs.contains(CurrencyPair.BTC_USD)).isTrue();
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenPublicTradesResult krakenTrades = mapper.readValue(is, KrakenPublicTradesResult.class);

    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getResult().getTrades(), CurrencyPair.BTC_USD, krakenTrades.getResult().getLast());

    Assert.assertEquals(14, trades.getTrades().size());
    assertThat(trades.getTrades().get(0).getPrice()).isEqualTo("1023.82219");
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(trades.getTrades().get(0).getTimestamp()).isEqualTo(new Date(1385579841777L));
    assertThat(trades.getTrades().get(1).getTradableAmount()).isEqualTo("0.01500000");
    assertThat(trades.getlastID()).isEqualTo(1385579841881785998L);

  }

  @Test
  public void testAdaptOrderBook() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenDepthResult krakenDepthResult = mapper.readValue(is, KrakenDepthResult.class);
    Map<String, KrakenDepth> krakenDepths = krakenDepthResult.getResult();
    String krakenAssetPair = "XXBTZEUR";
    KrakenDepth krakenDepth = krakenDepths.get(krakenAssetPair);

    OrderBook orderBook = KrakenAdapters.adaptOrderBook(krakenDepth, CurrencyPair.BTC_EUR);

    List<LimitOrder> asks = orderBook.getAsks();

    assertThat(asks.size()).isEqualTo(3);
    LimitOrder order = asks.get(0);
    assertThat(order.getLimitPrice()).isEqualTo(new BigDecimal("530.75513"));
    assertThat(order.getTradableAmount()).isEqualTo("0.248");
    assertThat(order.getTimestamp()).isEqualTo(new Date(1391825343000L));
  }

  @Test
  public void testAdaptBalance() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/account/example-balance-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenBalanceResult krakenBalance = mapper.readValue(is, KrakenBalanceResult.class);

    AccountInfo info = KrakenAdapters.adaptBalance(krakenBalance.getResult(), null);

    assertThat(info.getBalance(Currencies.EUR)).isEqualTo(new BigDecimal("1.0539"));
    assertThat(info.getBalance(Currencies.BTC)).isEqualTo(new BigDecimal("0.4888583300"));

  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/trading/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);

    OpenOrders orders = KrakenAdapters.adaptOpenOrders(krakenResult.getResult().getOrders());

    // Verify that the example data was unmarshalled correctly
    assertThat(orders.getOpenOrders()).hasSize(1);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("OR6QMM-BCKM4-Q6YHIN");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice()).isEqualTo("13.00000");
    assertThat(orders.getOpenOrders().get(0).getTradableAmount()).isEqualTo("0.01000000");
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().baseSymbol).isEqualTo(Currencies.LTC);
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().counterSymbol).isEqualTo(Currencies.EUR);
    assertThat(orders.getOpenOrders().get(0).getType()).isEqualTo(OrderType.BID);
  }

  @Test
  public void testAdaptOpenOrdersInTransactionCurrency() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/trading/example-openorders-in-transaction-currency-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);

    OpenOrders orders = KrakenAdapters.adaptOpenOrders(krakenResult.getResult().getOrders());

    // Verify that the example data was unmarshalled correctly
    assertThat(orders.getOpenOrders()).hasSize(1);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("OR6QMM-BCKM4-Q6YHIN");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice()).isEqualTo("500.00000");
    assertThat(orders.getOpenOrders().get(0).getTradableAmount()).isEqualTo("1.00000000");
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().baseSymbol).isEqualTo(Currencies.BTC);
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().counterSymbol).isEqualTo(Currencies.EUR);
    assertThat(orders.getOpenOrders().get(0).getType()).isEqualTo(OrderType.BID);
  }

  @Test
  public void testAdaptTradeHistory() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/trading/example-tradehistory-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradeHistoryResult krakenResult = mapper.readValue(is, KrakenTradeHistoryResult.class);
    KrakenTradeHistory krakenTradeHistory = krakenResult.getResult();
    Map<String, KrakenTrade> krakenTradeHistoryMap = krakenTradeHistory.getTrades();

    Trades trades = KrakenAdapters.adaptTradesHistory(krakenTradeHistoryMap);
    List<Trade> tradeList = trades.getTrades();

    assertThat(tradeList.size()).isEqualTo(1);
    Trade trade = tradeList.get(0);
    assertThat(trade.getId()).isEqualTo("TY5BYV-WJUQF-XPYEYD");
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("32.07562"));
    assertThat(trade.getTradableAmount()).isEqualTo("0.50000000");
    assertThat(trade.getCurrencyPair().baseSymbol).isEqualTo(Currencies.BTC);
    assertThat(trade.getCurrencyPair().counterSymbol).isEqualTo(Currencies.LTC);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
  }
}
