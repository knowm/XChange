/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v2.dto.account.polling.MtGoxWallet;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxDepthUpdate;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v2.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the MtGoxAdapter class
 */
public class MtGoxAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxAccountInfo mtGoxAccountInfo = mapper.readValue(is, MtGoxAccountInfo.class);

    AccountInfo accountInfo = MtGoxAdapters.adaptAccountInfo(mtGoxAccountInfo);
    assertThat(accountInfo.getUsername()).isEqualTo("xchange");
    assertThat(accountInfo.getWallets().get(0).getCurrency()).isEqualTo("BTC");
    assertThat(accountInfo.getWallets().get(0).getBalance()).isEqualTo(MoneyUtils.parse("BTC 0.00000000"));
  }

  @Test
  public void testOrderAdapterWithOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/trade/polling/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxOpenOrder[] mtGoxOpenOrders = mapper.readValue(is, MtGoxOpenOrder[].class);

    List<LimitOrder> openorders = MtGoxAdapters.adaptOrders(mtGoxOpenOrders);
    assertThat(openorders.size()).isEqualTo(38);

    // verify all fields filled
    System.out.println(openorders.get(0).toString());
    assertThat(openorders.get(0).getLimitPrice().getAmount().doubleValue()).isEqualTo(1.25);
    assertThat(openorders.get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(openorders.get(0).getTradableAmount().doubleValue()).isEqualTo(0.23385868);
    assertThat(openorders.get(0).getTradableIdentifier()).isEqualTo("BTC");
    assertThat(openorders.get(0).getTransactionCurrency()).isEqualTo("USD");

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(openorders.get(0).getTimestamp());
    assertThat(dateString).isEqualTo("2012-04-08 14:59:11");
  }

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/marketdata/polling/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxDepth mtGoxDepth = mapper.readValue(is, MtGoxDepth.class);

    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepth.getAsks(), "USD", "ask", "id_567");
    System.out.println(asks.size());
    assertThat(asks.size()).isEqualTo(503);

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().getAmount().doubleValue()).isEqualTo(182.99999);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(2.46297453);
    assertThat(asks.get(0).getTradableIdentifier()).isEqualTo("BTC");
    assertThat(asks.get(0).getTransactionCurrency()).isEqualTo("USD");

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(asks.get(0).getTimestamp());
    assertThat(dateString).isEqualTo("2013-04-08 18:09:23");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/marketdata/polling/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxTrade[] mtGoxTrades = mapper.readValue(is, MtGoxTrade[].class);

    Trades trades = MtGoxAdapters.adaptTrades(mtGoxTrades);
    assertThat(trades.getTrades().size()).isEqualTo(30);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().getAmount().doubleValue()).isEqualTo(193.99989);
    assertThat(trades.getTrades().get(0).getPrice().getAmount().floatValue()).isEqualTo(193.99989f);
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue()).isEqualTo(0.01985186);
    assertThat(trades.getTrades().get(0).getTradableIdentifier()).isEqualTo("BTC");
    assertThat(trades.getTrades().get(0).getTransactionCurrency()).isEqualTo("USD");
    assertThat(trades.getTrades().get(0).getId()).isEqualTo(1365499103363494L);
    // Unix 1334177326 = Wed, 11 Apr 2012 20:48:46 GMT
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-04-09 09:18:23 GMT");
  }

  @Test
  public void testWalletAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxAccountInfo mtGoxAccountInfo = mapper.readValue(is, MtGoxAccountInfo.class);

    // in MtGoxAccountInfo.getWallets, no wallets are null
    MtGoxWallet cadWallet = mtGoxAccountInfo.getWallets().getCAD();
    assertThat(cadWallet).isNull();
    MtGoxWallet usdWallet = mtGoxAccountInfo.getWallets().getUSD();
    assertThat(usdWallet).isNotNull();

    // in Wallet(s), only wallets from MtGoxAccountInfo.getWallets that contained data are NOT null.
    List<Wallet> wallets = MtGoxAdapters.adaptWallets(mtGoxAccountInfo.getWallets());
    assertThat(wallets.size()).isEqualTo(2);

    assertThat(wallets.contains(new Wallet(Currencies.CAD, MoneyUtils.parse("CAD 0.0")))).isFalse();
    assertThat(wallets.contains(new Wallet(Currencies.BTC, MoneyUtils.parse("BTC 0.00000000")))).isTrue();

    assertThat(wallets.get(0).getBalance().getAmount().doubleValue()).isEqualTo(0.0);
    assertThat(wallets.get(0).getBalance().getCurrencyUnit().toString()).isEqualTo("BTC");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/marketdata/polling/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);
    assertThat(ticker.getLast()).isEqualTo(MoneyUtils.parse("USD 91.46000"));
    assertThat(ticker.getBid()).isEqualTo(MoneyUtils.parse("USD 90.68502"));
    assertThat(ticker.getAsk()).isEqualTo(MoneyUtils.parse("USD 91.45898"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("49524.15110020"));

  }

  @Test
  public void testOrderBookUpdateAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v2/marketdata/streaming/example-depth-streaming-data.json");

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Map<String, Object> userInMap = mapper.readValue(is, new TypeReference<Map<String, Object>>() {
    });

    MtGoxDepthUpdate mtGoxDepthUpdate = mapper.readValue(mapper.writeValueAsString(userInMap.get("depth")), MtGoxDepthUpdate.class);

    OrderBookUpdate orderBookUpdate = MtGoxAdapters.adaptDepthUpdate(mtGoxDepthUpdate);

    assertThat(orderBookUpdate.getTotalVolume()).isEqualTo(new BigDecimal("324.02839775"));
    assertThat(orderBookUpdate.getLimitOrder().getTradableAmount()).isEqualTo(new BigDecimal("4.97732719"));
    assertThat(orderBookUpdate.getLimitOrder().getTimestamp().getTime()).isEqualTo(1364643714372L);

  }
}
