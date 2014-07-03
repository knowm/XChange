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
package com.xeiam.xchange.bitcurex.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitcurex.BitcurexAdapters;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexDepth;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexFunds;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTicker;
import com.xeiam.xchange.bitcurex.dto.marketdata.BitcurexTrade;
import com.xeiam.xchange.bitcurex.service.marketdata.BitcurexAccountJSONTest;
import com.xeiam.xchange.bitcurex.service.marketdata.BitcurexDepthJSONTest;
import com.xeiam.xchange.bitcurex.service.marketdata.BitcurexTickerJSONTest;
import com.xeiam.xchange.bitcurex.service.marketdata.BitcurexTradesJSONTest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the BitcurexAdapter class
 */
public class BitcurexAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexDepth BitcurexDepth = mapper.readValue(is, BitcurexDepth.class);

    List<LimitOrder> asks = BitcurexAdapters.adaptOrders(BitcurexDepth.getAsks(), CurrencyPair.BTC_EUR, OrderType.ASK, "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(70.00000000);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(0.1021341);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexTrade[] BitcurexTrades = mapper.readValue(is, BitcurexTrade[].class);

    Trades trades = BitcurexAdapters.adaptTrades(BitcurexTrades, CurrencyPair.BTC_EUR);
    assertThat(trades.getTrades().size()).isEqualTo(70);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 70.00000000);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 23.99500000);
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-07-29 16:53:28 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexTicker BitcurexTicker = mapper.readValue(is, BitcurexTicker.class);

    Ticker ticker = BitcurexAdapters.adaptTicker(BitcurexTicker, CurrencyPair.BTC_EUR);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("70");
    assertThat(ticker.getLow().toString()).isEqualTo("63.66");
    assertThat(ticker.getHigh().toString()).isEqualTo("70");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("103.23546591"));

  }

  @Test
  public void testAccountFundsAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-eur-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    AccountInfo accountInfo = BitcurexAdapters.adaptAccountInfo(bitcurexFunds, "demo");
    System.out.println(accountInfo.toString());

    assertThat(accountInfo.getBalance("BTC").compareTo( new BigDecimal("2.59033845"))==0);
    assertThat(accountInfo.getBalance("EUR").compareTo( new BigDecimal("6160.06838790"))==0);
    assertThat(accountInfo.getUsername().toString()).isEqualTo("demo");
  }

  @Test
  public void testAccountFundsPLNAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitcurexAccountJSONTest.class.getResourceAsStream("/marketdata/example-funds-pln-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcurexFunds bitcurexFunds = mapper.readValue(is, BitcurexFunds.class);

    AccountInfo accountInfo = BitcurexAdapters.adaptAccountInfo(bitcurexFunds, "demo");
    System.out.println(accountInfo.toString());

    assertThat(accountInfo.getBalance("BTC").compareTo(new BigDecimal("2.59033845")) == 0);
    assertThat(accountInfo.getBalance("PLN").compareTo(new BigDecimal("6160.06838790")) == 0);
    assertThat(accountInfo.getUsername().toString()).isEqualTo("demo");
  }

}
