package org.knowm.xchange.kraken;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.account.results.KrakenBalanceResult;
import org.knowm.xchange.kraken.dto.account.results.KrakenLedgerResult;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenTickerResult;
import org.knowm.xchange.kraken.dto.trade.KrakenTrade;
import org.knowm.xchange.kraken.dto.trade.KrakenUserTrade;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult.KrakenTradeHistory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

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
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("576.77284"));
    assertThat(ticker.getVolume()).isEqualByComparingTo("600.91850325");
    assertThat(ticker.getCurrencyPair().base.getCurrencyCode()).isEqualTo(currencyPair.base.getCurrencyCode());
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

    Wallet wallet = KrakenAdapters.adaptWallet(krakenBalance.getResult());

    assertThat(wallet.getBalance(Currency.EUR).getTotal()).isEqualTo(new BigDecimal("1.0539"));
    assertThat(wallet.getBalance(Currency.BTC).getTotal()).isEqualTo(new BigDecimal("0.4888583300"));

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
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().base).isEqualTo(Currency.LTC);
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().counter).isEqualTo(Currency.EUR);
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
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().base).isEqualTo(Currency.BTC);
    assertThat(orders.getOpenOrders().get(0).getCurrencyPair().counter).isEqualTo(Currency.EUR);
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

    UserTrades trades = KrakenAdapters.adaptTradesHistory(krakenTradeHistoryMap);
    List<UserTrade> tradeList = trades.getUserTrades();

    assertThat(tradeList.size()).isEqualTo(1);
    UserTrade trade = tradeList.get(0);
    assertThat(trade).isInstanceOf(KrakenUserTrade.class);
    assertThat(trade.getId()).isEqualTo("TY5BYV-WJUQF-XPYEYD");
    assertThat(trade.getPrice()).isEqualTo("32.07562");
    assertThat(trade.getTradableAmount()).isEqualTo("0.50000000");
    assertThat(trade.getCurrencyPair().base).isEqualTo(Currency.BTC);
    assertThat(trade.getCurrencyPair().counter).isEqualTo(Currency.LTC);
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getFeeAmount()).isEqualTo("0.03208");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.LTC);
    assertThat(((KrakenUserTrade) trade).getCost()).isEqualTo("16.03781");
  }

  @Test
  public void testAdaptFundingHistory() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAdaptersTest.class.getResourceAsStream("/account/example-ledgerinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenLedgerResult krakenResult = mapper.readValue(is, KrakenLedgerResult.class);
    KrakenLedgerResult.KrakenLedgers ledgers = krakenResult.getResult();
    Map<String, KrakenLedger> ledgerMap = ledgers.getLedgerMap();

    List<FundingRecord> records = KrakenAdapters.adaptFundingHistory(ledgerMap);

    assertThat(records.size()).isEqualTo(3);
    FundingRecord fundingRecord = records.get(1);
    assertThat(fundingRecord).isInstanceOf(FundingRecord.class);
    assertThat(fundingRecord.getType()).isEqualTo("WITHDRAWAL");
    assertThat(fundingRecord.getAmount()).isEqualTo(new BigDecimal("-15.9857300000"));
    assertThat(fundingRecord.getFee().doubleValue()).isEqualTo(new BigDecimal("0.02").doubleValue());
    assertThat(fundingRecord.getBalance().doubleValue()).isEqualTo(BigDecimal.ZERO.doubleValue());
  }
}
