/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.account.KrakenBalanceResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTickerResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTradesResult;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenOrdersResult;
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
    assertThat(pairs).hasSize(17);
    assertThat(pairs).contains(CurrencyPair.BTC_EUR, CurrencyPair.LTC_EUR, CurrencyPair.BTC_USD);
  }

  @Test
  public void testAdaptTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradesResult krakenTrades = mapper.readValue(is, KrakenTradesResult.class);

    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getResult().getTradesPerCurrencyPair("XXBTZUSD"), Currencies.EUR, Currencies.BTC, krakenTrades.getResult().getLast());
    Assert.assertEquals(14, trades.getTrades().size());
    assertThat(trades.getTrades().get(0).getTimestamp()).isBefore(new Date());
    assertThat(trades.getTrades().get(0).getPrice().getAmount()).isEqualTo("1023.82219");
    assertThat(trades.getTrades().get(1).getTradableAmount()).isEqualTo("0.01500000");

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
    assertThat(orders.getOpenOrders().get(0).getLimitPrice().getAmount()).isEqualTo("13.00000");
    assertThat(orders.getOpenOrders().get(0).getTradableAmount()).isEqualTo("0.01000000");
    assertThat(orders.getOpenOrders().get(0).getTradableIdentifier()).isEqualTo(Currencies.LTC);
    assertThat(orders.getOpenOrders().get(0).getTransactionCurrency()).isEqualTo(Currencies.EUR);

  }

  @Test
  public void testAdaptOpenOrdersInTransactionCurrency() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenOpenOrdersTest.class.getResourceAsStream("/trading/example-openorders-in-transaction-currency-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);
    OpenOrders orders = KrakenAdapters.adaptOpenOrders(krakenResult.getResult().getOrders());
    // Verify that the example data was unmarshalled correctly
    assertThat(orders.getOpenOrders()).hasSize(1);
    assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("OR6QMM-BCKM4-Q6YHIN");
    assertThat(orders.getOpenOrders().get(0).getLimitPrice().getAmount()).isEqualTo("500.00000");
    assertThat(orders.getOpenOrders().get(0).getTradableAmount()).isEqualTo("1.00000000");
    assertThat(orders.getOpenOrders().get(0).getTradableIdentifier()).isEqualTo(Currencies.BTC);
    assertThat(orders.getOpenOrders().get(0).getTransactionCurrency()).isEqualTo(Currencies.EUR);

  }

}
