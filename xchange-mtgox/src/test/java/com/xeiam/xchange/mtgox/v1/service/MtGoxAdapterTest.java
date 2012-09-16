/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.mtgox.v1.MtGoxAdapters;
import com.xeiam.xchange.mtgox.v1.dto.account.MtGoxAccountInfo;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxDepth;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTicker;
import com.xeiam.xchange.mtgox.v1.dto.marketdata.MtGoxTrade;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v1.dto.trade.MtGoxWallet;
import com.xeiam.xchange.mtgox.v1.service.account.AccountInfoJSONTest;
import com.xeiam.xchange.mtgox.v1.service.marketdata.DepthJSONTest;
import com.xeiam.xchange.mtgox.v1.service.marketdata.TickerJSONTest;
import com.xeiam.xchange.mtgox.v1.service.marketdata.TradesJSONTest;
import com.xeiam.xchange.mtgox.v1.service.trade.OpenOrdersJSONTest;
import com.xeiam.xchange.utils.MoneyUtils;

/**
 * Tests the MtGoxAdapter class
 */
public class MtGoxAdapterTest {

  @Test
  public void testOrderAdapterWithOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = OpenOrdersJSONTest.class.getResourceAsStream("/trade/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxOpenOrder[] mtGoxOpenOrders = mapper.readValue(is, MtGoxOpenOrder[].class);

    List<LimitOrder> openorders = MtGoxAdapters.adaptOrders(mtGoxOpenOrders);
    // System.out.println(openorders.size());
    assertTrue("ASKS size should be 38", openorders.size() == 38);

    // verify all fields filled
    // System.out.println(openorders.get(0).toString());
    assertTrue("limit price should be 1.25", openorders.get(0).getLimitPrice().getAmount().doubleValue() == 1.25);
    assertTrue("order type should be BID", openorders.get(0).getType() == OrderType.BID);
    assertTrue("tradableAmount should be 0.23385868", openorders.get(0).getTradableAmount().doubleValue() == 0.23385868);
    assertTrue("tradableIdentifier should be BTC", openorders.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", openorders.get(0).getTransactionCurrency().equals("USD"));

  }

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = DepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxDepth mtGoxDepth = mapper.readValue(is, MtGoxDepth.class);

    List<LimitOrder> asks = MtGoxAdapters.adaptOrders(mtGoxDepth.getAsks(), "USD", "ask", "id_567");
    // System.out.println(openorders.size());
    assertTrue("ASKS size should be 1582", asks.size() == 1582);

    // verify all fields filled
    // System.out.println(asks.get(0).toString());
    assertTrue("limit price should be 18.1", asks.get(0).getLimitPrice().getAmount().doubleValue() == 18.1);
    assertTrue("order type should be ASK", asks.get(0).getType() == OrderType.ASK);
    assertTrue("tradableAmount should be 20", asks.get(0).getTradableAmount().doubleValue() == 20.0);
    assertTrue("tradableIdentifier should be BTC", asks.get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be USD", asks.get(0).getTransactionCurrency().equals("USD"));

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTrade[] mtGoxTrades = mapper.readValue(is, MtGoxTrade[].class);

    Trades trades = MtGoxAdapters.adaptTrades(mtGoxTrades);
    // System.out.println(trades.getTrades().size());
    assertTrue("Trades size should be 90", trades.getTrades().size() == 90);

    // verify all fields filled
    // System.out.println(trades.getTrades().get(0).toString());
    assertTrue("price should be 15.6", trades.getTrades().get(0).getPrice().getAmount().doubleValue() == 15.6);
    assertTrue("order type should be ASK", trades.getTrades().get(0).getType() == OrderType.ASK);
    assertTrue("tradableAmount should be 0.7", trades.getTrades().get(0).getTradableAmount().doubleValue() == 0.7);
    assertTrue("tradableIdentifier should be BTC", trades.getTrades().get(0).getTradableIdentifier().equals("BTC"));
    assertTrue("transactionCurrency should be PLN", trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    // Unix 1334177326 = Wed, 11 Apr 2012 20:48:46 GMT
    assertEquals("timestamp incorrect", "2012-04-11T20:48:46.000Z", trades.getTrades().get(0).getTimestamp().toString());
  }

  @Test
  public void testWalletAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = AccountInfoJSONTest.class.getResourceAsStream("/account/example-accountinfo-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxAccountInfo mtGoxAccountInfo = mapper.readValue(is, MtGoxAccountInfo.class);

    // in MtGoxAccountInfo.getWallets, no wallets are null
    MtGoxWallet CADWallet = mtGoxAccountInfo.getWallets().getCAD();
    assertTrue("CAD should be null", CADWallet == null);
    MtGoxWallet USDWallet = mtGoxAccountInfo.getWallets().getUSD();
    assertTrue("USD should NOT be null", USDWallet != null);

    // in Wallet(s), only wallets from MtGoxAccountInfo.getWallets that contained data are NOT null.
    List<com.xeiam.xchange.dto.trade.Wallet> wallets = MtGoxAdapters.adaptWallets(mtGoxAccountInfo.getWallets());
    assertTrue("List size should be true!", wallets.size() == 2);
    assertTrue("CAD should be null", !wallets.contains(new com.xeiam.xchange.dto.trade.Wallet(MoneyUtils.parseFiat("CAD 0.0"))));
    assertTrue("BTC should NOT be null", wallets.contains(new com.xeiam.xchange.dto.trade.Wallet(MoneyUtils.parseFiat("BTC 0.0"))));

    // System.out.println(wallets.get(0).toString());
    assertTrue("wallets.get(0).getBalance().getAmount().doubleValue() should be 0.0", wallets.get(0).getBalance().getAmount().doubleValue() == 0.0);
    assertTrue("wallets.get(0).getBalance().getCurrencyUnit().toString() should be BTC", wallets.get(0).getBalance().getCurrencyUnit().toString().equals("BTC"));
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    MtGoxTicker mtGoxTicker = mapper.readValue(is, MtGoxTicker.class);

    Ticker ticker = MtGoxAdapters.adaptTicker(mtGoxTicker);
    System.out.println(ticker.toString());

    assertEquals("last should be USD 4.91227", ticker.getLast().toString(), "USD 4.89");
    assertEquals("bid should be USD 4.91227", ticker.getBid().toString(), "USD 4.89002");
    assertEquals("ask should be USD 4.91227", ticker.getAsk().toString(), "USD 4.91227");
    assertEquals("volume should be USD 4.91227", ticker.getVolume(), 5775966891627L);

  }
}
