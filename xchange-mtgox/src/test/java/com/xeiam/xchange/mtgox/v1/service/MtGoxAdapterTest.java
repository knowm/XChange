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
package com.xeiam.xchange.mtgox.v1.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
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
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepthUpdate;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxWallet;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the MtGoxAdapter class
 */
public class MtGoxAdapterTest {

  @Test
  public void testAccountInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxAccountInfo mtGoxAccountInfo = mapper.readValue(is, MtGoxAccountInfo.class);

    AccountInfo accountInfo = MtGoxAdapters.adaptAccountInfo(mtGoxAccountInfo);
    assertThat(accountInfo.getUsername(), is(equalTo("xchange")));
    assertThat(accountInfo.getWallets().get(0).getCurrency(), is(equalTo("BTC")));
    assertThat(accountInfo.getWallets().get(0).getBalance(), is(equalTo(MoneyUtils.parse("BTC 0.00000000"))));
  }

  @Test
  public void testOrderAdapterWithOpenOrders() throws IOException {
    final String correctTimestampString = "2012-Apr-08 14:59:11";

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/trade/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxOpenOrder[] mtGoxOpenOrders = mapper.readValue(is, MtGoxOpenOrder[].class);

    List<LimitOrder> openorders = MtGoxAdapters.adaptOrders(mtGoxOpenOrders);
    // System.out.println(openorders.size());
    assertTrue("ASKS size should be 38", openorders.size() == 38);

    // verify all fields filled
    System.out.println(openorders.get(0).toString());
    assertTrue("limit price should be 1.25", openorders.get(0).getLimitPrice().getAmount().doubleValue() == 1.25);
    assertTrue("order type should be BID", openorders.get(0).getType() == OrderType.BID);
    assertTrue("tradableAmount should be 0.23385868", openorders.get(0).getTradableAmount().doubleValue() == 0.23385868);
    assertTrue("tradableIdentifier should be BTC", openorders.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", openorders.get(0).getTransactionCurrency().equals("USD"));

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.US );
    f.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dateString = f.format(openorders.get(0).getTimestamp());
    System.out.println(dateString);
    assertTrue( "dateString should be '" + correctTimestampString + "', but was '" + dateString + "'",
                dateString.equals( correctTimestampString ) );
  }

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/marketdata/polling/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxDepth mtGoxDepth = mapper.readValue(is, MtGoxDepth.class);

    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepth.getAsks(), "USD", "ask", "id_567");
    System.out.println(asks.size());
    assertTrue("ASKS size should be 503", asks.size() == 503);

    // verify all fields filled
    // System.out.println(asks.get(0).toString());
    assertThat(asks.get(0).getLimitPrice().getAmount().doubleValue(), equalTo(182.99999));
    assertTrue("order type should be ASK", asks.get(0).getType() == OrderType.ASK);
    assertThat("tradableAmount should be 20", asks.get(0).getTradableAmount().doubleValue(), equalTo(2.46297453));
    assertTrue("tradableIdentifier should be BTC", asks.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", asks.get(0).getTransactionCurrency().equals("USD"));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/marketdata/polling/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxTrade[] mtGoxTrades = mapper.readValue(is, MtGoxTrade[].class);

    Trades trades = MtGoxAdapters.adaptTrades(mtGoxTrades);
    // System.out.println(trades.getTrades().size());
    assertThat(trades.getTrades().size(), equalTo(30));

    // verify all fields filled
    // System.out.println(trades.getTrades().get(0).toString());
    assertThat(trades.getTrades().get(0).getPrice().getAmount().doubleValue(), equalTo(193.99989));
    assertThat(trades.getTrades().get(0).getPrice().getAmount().floatValue(), equalTo(193.99989f));
    assertThat(trades.getTrades().get(0).getType(), equalTo(OrderType.BID));
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue(), equalTo(0.01985186));
    assertThat(trades.getTrades().get(0).getTradableIdentifier(), equalTo("BTC"));
    assertThat(trades.getTrades().get(0).getTransactionCurrency(), equalTo("USD"));
    assertThat(trades.getTrades().get(0).getId(), equalTo(1365499103363494L));
    // Unix 1334177326 = Wed, 11 Apr 2012 20:48:46 GMT
    assertThat("2013-04-09 09:18:23 GMT", is(equalTo(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()))));
  }

  @Test
  public void testWalletAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxAccountInfo mtGoxAccountInfo = mapper.readValue(is, MtGoxAccountInfo.class);

    // in MtGoxAccountInfo.getWallets, no wallets are null
    MtGoxWallet CADWallet = mtGoxAccountInfo.getWallets().getCAD();
    assertTrue("CAD should be null", CADWallet == null);
    MtGoxWallet USDWallet = mtGoxAccountInfo.getWallets().getUSD();
    assertTrue("USD should NOT be null", USDWallet != null);

    // in Wallet(s), only wallets from MtGoxAccountInfo.getWallets that contained data are NOT null.
    List<Wallet> wallets = MtGoxAdapters.adaptWallets(mtGoxAccountInfo.getWallets());
    // System.out.println(wallets.toString());
    assertTrue("List size should be true!", wallets.size() == 2);
    assertTrue("CAD should be null", !wallets.contains(new Wallet(Currencies.CAD, MoneyUtils.parse("CAD 0.0"))));
    assertTrue("BTC should NOT be null", wallets.contains(new Wallet(Currencies.BTC, MoneyUtils.parse("BTC 0.00000000"))));

    // System.out.println(wallets.get(0).toString());
    assertTrue("wallets.get(0).getBalance().getAmount().doubleValue() should be 0.0", wallets.get(0).getBalance().getAmount().doubleValue() == 0.0);
    assertTrue("wallets.get(0).getBalance().getCurrencyUnit().toString() should be BTC", wallets.get(0).getBalance().getCurrencyUnit().toString().equals("BTC"));
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/marketdata/polling/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);
    // System.out.println(ticker.toString());

    assertThat(ticker.getLast(), is(equalTo(MoneyUtils.parse("USD 91.46000"))));
    assertThat(ticker.getBid(), is(equalTo(MoneyUtils.parse("USD 90.68502"))));
    assertThat(ticker.getAsk(), is(equalTo(MoneyUtils.parse("USD 91.45898"))));
    assertThat(ticker.getVolume(), is(equalTo(new BigDecimal("49524.15110020"))));

  }

  @Test
  public void testOrderBookUpdateAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = MtGoxAdapterTest.class.getResourceAsStream("/v1/marketdata/streaming/example-depth-streaming-data.json");

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    Map<String, Object> userInMap = mapper.readValue(is, new TypeReference<Map<String, Object>>() {
    });

    MtGoxDepthUpdate mtGoxDepthUpdate = mapper.readValue(mapper.writeValueAsString(userInMap.get("depth")), MtGoxDepthUpdate.class);

    OrderBookUpdate orderBookUpdate = MtGoxAdapters.adaptDepthUpdate(mtGoxDepthUpdate);
    // System.out.println(orderBookUpdate.toString());

    assertThat(orderBookUpdate.getTotalVolume(), is(equalTo(new BigDecimal("324.02839775"))));
    assertThat(orderBookUpdate.getLimitOrder().getTradableAmount(), is(equalTo(new BigDecimal("4.97732719"))));
    assertThat(orderBookUpdate.getLimitOrder().getTimestamp().getTime(), is(equalTo(1364643714372L)));

  }
}
