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
package com.xeiam.xchange.examples.anx.v2;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXExchange;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.account.polling.ANXWalletHistoryWrapper;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Tests the ANXAdapter class
 */
@Ignore
public class ANXTest {

  Exchange anx;

  @Before
  public void setUp() {

    ExchangeSpecification exSpec = new ExchangeSpecification(ANXExchange.class);

    // xchange.anx@gmail.com
    exSpec.setApiKey("354aba43-0ec3-42c6-9667-335dc2e2efcb");
    exSpec.setSecretKey("EtrQxjbV6lCxCQRRv7flDS1KB4ec1z7CwKzYuCmudt/as9u18yZh+i0cq62x4Au3fAhnzrDFE8SZWJw8iTpDQA==");

    String protocol = "https";
    String host = "anxpro.com";
    int port = 443;
    exSpec.setSslUri(protocol + "://" + host + ":" + port);
    exSpec.setHost(host);
    exSpec.setPort(port);

    anx = ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @Test
  public void testAccountInfo() throws IOException {

    // Interested in the private account functionality (authentication)
    PollingAccountService accountService = anx.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();

    // Verify that the example data was unmarshalled correctly
    Assert.assertEquals("xchange.anx@gmail.com", accountInfo.getUsername());
  }

  @Test
  public void testBTCDepositAddress() throws IOException {

    PollingAccountService accountService = anx.getPollingAccountService();

    // Request a Bitcoin deposit address
    String address = accountService.requestDepositAddress(Currencies.BTC.toString());
    Assert.assertNotNull(address);
    System.out.println("Address to deposit Bitcoins to: " + address);
  }

  @Test
  public void testDOGEDepositAddress() throws IOException {

    PollingAccountService accountService = anx.getPollingAccountService();

    String address = accountService.requestDepositAddress(Currencies.DOGE.toString());
    Assert.assertNotNull(address);
    System.out.println("Address to deposit Dogecoin to: " + address);
  }

  @Ignore
  @Test
  public void testBTCWithdrawal() throws IOException {

    PollingAccountService accountService = anx.getPollingAccountService();

    // ANX does not return a transaction id on fund withdrawal at this moment
    String success = accountService.withdrawFunds(Currencies.BTC.toString(), new BigDecimal("0.001"), "1DTZHQF47QzETutRRQVr2o2Rjcku8gBWft");
    Assert.assertEquals("success", success);
  }

  @Ignore
  @Test
  public void testDOGEWithdrawal() throws IOException {

    PollingAccountService accountService = anx.getPollingAccountService();

    // ANX does not return a transaction id on fund withdrawal at this moment
    String success = accountService.withdrawFunds(Currencies.DOGE.toString(), new BigDecimal("2"), "D8Sg7gUEp3vYc47qtsugHuxVVb1azPBxsW");
    Assert.assertEquals("success", success);
  }

  @Test
  public void testBTCWalletHistory() throws IOException {

    ANXV2 anxv2 = RestProxyFactory.createProxy(ANXV2.class, anx.getExchangeSpecification().getSslUri());
    ParamsDigest signatureCreator = ANXV2Digest.createInstance(anx.getExchangeSpecification().getSecretKey());

    ANXWalletHistoryWrapper walletHistory = anxv2.getWalletHistory(anx.getExchangeSpecification().getApiKey(), signatureCreator, ANXUtils.getNonce(), Currencies.BTC.toString(), 1);
    Assert.assertEquals(50, walletHistory.getANXWalletHistory().getMaxResults());
    Assert.assertEquals(1, walletHistory.getANXWalletHistory().getCurrentPage());

    walletHistory = anxv2.getWalletHistory(anx.getExchangeSpecification().getApiKey(), signatureCreator, ANXUtils.getNonce(), Currencies.BTC.toString(), 2);
    Assert.assertEquals(2, walletHistory.getANXWalletHistory().getCurrentPage());
  }

  @Test
  public void testDOGEWalletHistory() throws IOException {

    ANXV2 anxv2 = RestProxyFactory.createProxy(ANXV2.class, anx.getExchangeSpecification().getSslUri());
    ParamsDigest signatureCreator = ANXV2Digest.createInstance(anx.getExchangeSpecification().getSecretKey());

    ANXWalletHistoryWrapper walletHistory = anxv2.getWalletHistory(anx.getExchangeSpecification().getApiKey(), signatureCreator, ANXUtils.getNonce(), Currencies.DOGE.toString(), 1);
    Assert.assertEquals(50, walletHistory.getANXWalletHistory().getMaxResults());
    Assert.assertEquals(1, walletHistory.getANXWalletHistory().getCurrentPage());

    walletHistory = anxv2.getWalletHistory(anx.getExchangeSpecification().getApiKey(), signatureCreator, ANXUtils.getNonce(), Currencies.DOGE.toString(), 2);
    Assert.assertEquals(2, walletHistory.getANXWalletHistory().getCurrentPage());
  }

  @Test
  public void testDepth() throws IOException {

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    // Get the current full orderbook
    OrderBook fullOrderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    // fullOrderBook.getTimeStamp()
    Assert.assertTrue(fullOrderBook.getAsks().size() > 0);
    Assert.assertTrue(fullOrderBook.getBids().size() > 0);
    for (LimitOrder limitOrder : fullOrderBook.getAsks()) {
      System.out.println(limitOrder);
    }
    int i = 0;
    for (LimitOrder limitOrder : fullOrderBook.getBids()) {
      System.out.println(i + " : " + limitOrder);
      i++;
    }
  }

  @Test
  public void testTicker() throws IOException {

    // Interested in the private account functionality (authentication)
    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker;

    ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    Assert.assertEquals(CurrencyPair.BTC_USD, ticker.getCurrencyPair());

    ticker = marketDataService.getTicker(CurrencyPair.LTC_BTC);
    Assert.assertEquals(CurrencyPair.LTC_BTC, ticker.getCurrencyPair());

    ticker = marketDataService.getTicker(CurrencyPair.DOGE_BTC);
    Assert.assertEquals(CurrencyPair.DOGE_BTC, ticker.getCurrencyPair());
  }

  @Test
  public void testOpenOrders() throws IOException {

    PollingTradeService tradeService = anx.getPollingTradeService();

    // Get the open orders
    OpenOrders openOrders = tradeService.getOpenOrders();
  }

  @Ignore
  @Test
  public void testLimitOrderAndCancelBTC() throws IOException, InterruptedException {

    PollingTradeService tradeService = anx.getPollingTradeService();

    // place a limit order for a random amount of BTC at USD 1.25
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("2");
    BigDecimal limitPrice = new BigDecimal("802");

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_USD, "", null, limitPrice);

    String orderID = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order ID: " + orderID);

    Thread.sleep(5000);

    // get open orders
    OpenOrders openOrders = tradeService.getOpenOrders();

    boolean found = ANXUtils.findLimitOrder(openOrders.getOpenOrders(), limitOrder, orderID);
    if (!found) {
      Assert.fail("Order not found");
    }

    // Cancel order
    boolean success = tradeService.cancelOrder(orderID);
    Assert.assertTrue(success);

    Thread.sleep(5000);

    // get open orders
    openOrders = tradeService.getOpenOrders();

    found = ANXUtils.findLimitOrder(openOrders.getOpenOrders(), limitOrder, orderID);
    if (found) {
      Assert.fail("Cancelled Order found");
    }
  }

  @Ignore
  @Test
  public void testLimitOrderDOGE() throws IOException, InterruptedException {

    PollingTradeService tradeService = anx.getPollingTradeService();

    // place a limit order for a random amount of BTC at USD 1.25
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("25000");
    BigDecimal limitPrice = new BigDecimal("0.02");

    LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.DOGE_USD, "", null, limitPrice);

    String orderID = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order ID: " + orderID);

    Thread.sleep(5000);

    // get open orders
    OpenOrders openOrders = tradeService.getOpenOrders();

    boolean found = ANXUtils.findLimitOrder(openOrders.getOpenOrders(), limitOrder, orderID);
    if (!found) {
      Assert.fail("Order not found");
    }
  }

  @Ignore
  @Test
  public void testMarketOrderBTC() throws IOException, InterruptedException {

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = anx.getPollingTradeService();

    // place a market order for 1 Bitcoin at market price
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_USD, new Date());

    String orderID = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + orderID);

  }
}
