package com.xeiam.xchange.kraken.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.junit.Assert;
import org.junit.Test;
import org.xchange.kraken.KrakenAdapters;
import org.xchange.kraken.dto.account.KrakenBalanceResult;
import org.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import org.xchange.kraken.dto.marketdata.KrakenTickerResult;
import org.xchange.kraken.dto.marketdata.KrakenTradesResult;
import org.xchange.kraken.dto.trade.KrakenOpenOrdersResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.kraken.service.marketdata.KrakenAssetPairsJSONTest;
import com.xeiam.xchange.kraken.service.marketdata.KrakenTickerJSONTest;
import com.xeiam.xchange.kraken.service.marketdata.KrakenTradesJSONTest;
import com.xeiam.xchange.kraken.service.trading.KrakenOpenOrdersTest;

public class KrakenAdaptersTest {



  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTickerResult krakenTicker = mapper.readValue(is, KrakenTickerResult.class);
    Ticker ticker = KrakenAdapters.adaptTicker(krakenTicker.getResult().get("XBTCZEUR"), Currencies.EUR, Currencies.BTC);
    // Verify that the example data was unmarshalled correctly
    assertThat(ticker.getAsk()).isEqualTo(BigMoney.of(CurrencyUnit.EUR, new BigDecimal("96.99000")));
    assertThat(ticker.getBid().getAmount()).isEqualByComparingTo("96.0");
    assertThat(ticker.getLast().getAmount()).isEqualByComparingTo("96.75");
    assertThat(ticker.getVolume()).isZero();
  }

  @Test
  public void testAdaptCurrencyPairs() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenAssetPairsJSONTest.class.getResourceAsStream("/marketdata/example-assetpairs-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenAssetPairsResult krakenAssetPairs = mapper.readValue(is, KrakenAssetPairsResult.class);

    List<CurrencyPair> pairs = KrakenAdapters.adaptCurrencyPairs(krakenAssetPairs.getResult().keySet());
    assertThat(pairs).hasSize(5);
    assertThat(pairs).contains(CurrencyPair.BTC_EUR, CurrencyPair.LTC_EUR, CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradesResult krakenTrades = mapper.readValue(is, KrakenTradesResult.class);

    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getResult().getTradesPerCurrencyPair("XXBTZEUR"), Currencies.EUR, Currencies.BTC, krakenTrades.getResult().getLast());
    Assert.assertEquals(2, trades.getTrades().size());
    assertThat(trades.getTrades().get(0).getTimestamp()).isBefore(new Date());
    assertThat(trades.getTrades().get(0).getPrice().getAmount()).isEqualTo("92.50000");
    assertThat(trades.getTrades().get(1).getTradableAmount()).isEqualTo("0.05506000");

  }

  @Test
  public void testAdaptBalance() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradesJSONTest.class.getResourceAsStream("/account/example-balance-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenBalanceResult krakenBalance = mapper.readValue(is, KrakenBalanceResult.class);
    AccountInfo info = KrakenAdapters.adaptBalance(krakenBalance, null);
    assertThat(info.getBalance(CurrencyUnit.EUR)).isEqualTo(BigMoney.of(CurrencyUnit.EUR, new BigDecimal("1.0539")));
    assertThat(info.getBalance(CurrencyUnit.of(Currencies.BTC))).isEqualTo(BigMoney.of(CurrencyUnit.of(Currencies.BTC), new BigDecimal("0.4888583300")));

  }

  @Test
  public void testAdaptOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenOpenOrdersTest.class.getResourceAsStream("/trading/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);
    OpenOrders orders = KrakenAdapters.adaptOpenOrders(krakenResult.getResult().getOrders());
    // Verify that the example data was unmarshalled correctly
    assertThat(orders.getOpenOrders()).hasSize(1);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("OR6QMM-BCKM4-Q6YHIN");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice().getAmount()).isEqualTo("1.00000");
    assertThat(orders.getOpenOrders().get(0).getTradableAmount()).isEqualTo("1.00000000");
    assertThat(orders.getOpenOrders().get(0).getTradableIdentifier()).isEqualTo(Currencies.BTC);
    assertThat(orders.getOpenOrders().get(0).getTransactionCurrency()).isEqualTo(Currencies.EUR);

  }
}
