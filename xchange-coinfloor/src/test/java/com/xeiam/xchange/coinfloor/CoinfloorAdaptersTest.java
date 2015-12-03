package com.xeiam.xchange.coinfloor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.Wallet;
import org.junit.Assert;
import org.junit.Test;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * @author obsessiveOrange
 */

public class CoinfloorAdaptersTest {

  private Wallet cachedWallet;
  private Trades cachedTrades;
  private OrderBook cachedOrderBook;
  private CoinfloorAdapters coinfloorAdapters = new CoinfloorAdapters();

  @Test
  public void testAdaptBalances() throws ExchangeException, IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/account/example-balances-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptBalances(result);

    Assert.assertEquals(BigDecimal.valueOf(100014718, 4), ((Wallet) testObj.get("generic")).getBalance(Currency.BTC).getTotal());
    Assert.assertEquals(BigDecimal.valueOf(931913, 2), ((Wallet) testObj.get("generic")).getBalance(Currency.GBP).getTotal());
  }

  @Test
  public void testAdaptOpenOrders() throws ExchangeException, IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/trade/example-openOrders-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptOpenOrders(result);

    Assert.assertEquals(BigDecimal.valueOf(10000, 4), ((OpenOrders) testObj.get("generic")).getOpenOrders().get(0).getTradableAmount());
    Assert.assertEquals("211118", ((OpenOrders) testObj.get("generic")).getOpenOrders().get(0).getId());
    Assert.assertEquals("GBP", ((OpenOrders) testObj.get("generic")).getOpenOrders().get(0).getCurrencyPair().counter.getCurrencyCode());
    Assert.assertEquals("BTC", ((OpenOrders) testObj.get("generic")).getOpenOrders().get(0).getCurrencyPair().base.getCurrencyCode());
    Assert.assertEquals(OrderType.BID, ((OpenOrders) testObj.get("generic")).getOpenOrders().get(0).getType());

  }

  @Test
  public void testAdaptPlaceOrder() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/trade/example-placeOrder-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptPlaceOrder(result);

    Assert.assertEquals("211120", (String.valueOf(testObj.get("generic"))));
  }

  @Test
  public void testAdaptTradeVolume() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/account/example-tradeVolume-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptTradeVolume(result);

    Assert.assertEquals(BigDecimal.valueOf(40070, 4), new BigDecimal(String.valueOf(testObj.get("generic"))));
  }

  @Test
  public void testAdaptTicker() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/marketdata/example-ticker-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptTicker(result);

    Assert.assertEquals(BigDecimal.valueOf(32000, 2), ((Ticker) testObj.get("generic")).getLast());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), ((Ticker) testObj.get("generic")).getHigh());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), ((Ticker) testObj.get("generic")).getLow());
    Assert.assertEquals(BigDecimal.valueOf(33000, 2), ((Ticker) testObj.get("generic")).getAsk());
    Assert.assertEquals(BigDecimal.valueOf(32000, 2), ((Ticker) testObj.get("generic")).getBid());
  }

  @Test
  public void testAdaptTickerUpdate() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/marketdata/example-ticker-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    coinfloorAdapters.adaptTicker(result);

    // Read in the JSON from the example resources
    BufferedReader br2 = new BufferedReader(new FileReader("src/test/resources/marketdata/example-ticker-update.json"));

    String result2 = "", line2;
    while ((line2 = br2.readLine()) != null) {
      result2 += line2.trim();
    }

    br2.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptTickerUpdate(result2);

    Assert.assertEquals(BigDecimal.valueOf(32000, 2), ((Ticker) testObj.get("generic")).getLast());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), ((Ticker) testObj.get("generic")).getHigh());
    Assert.assertEquals(BigDecimal.valueOf(0, 2), ((Ticker) testObj.get("generic")).getLow());
    Assert.assertEquals(BigDecimal.valueOf(31899, 2), ((Ticker) testObj.get("generic")).getAsk());
    Assert.assertEquals(BigDecimal.valueOf(32000, 2), ((Ticker) testObj.get("generic")).getBid());
  }

  @Test
  public void testAdaptOrders() throws ExchangeException, IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/marketdata/example-orders-response.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptOrders(result);

    Assert.assertEquals(7, ((OrderBook) testObj.get("generic")).getAsks().size());
    Assert.assertEquals(7, ((OrderBook) testObj.get("generic")).getBids().size());
    Assert.assertEquals(BigDecimal.valueOf(9983, 4), ((OrderBook) testObj.get("generic")).getBids().get(0).getTradableAmount());
  }

  @Test
  public void testAdaptOrderOpened() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/marketdata/example-orderOpened-update.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptOrderOpened(result);

    Assert.assertEquals(BigDecimal.valueOf(10000, 4), ((LimitOrder) testObj.get("generic")).getTradableAmount());
    Assert.assertEquals("GBP", ((LimitOrder) testObj.get("generic")).getCurrencyPair().counter.getCurrencyCode());
    Assert.assertEquals("BTC", ((LimitOrder) testObj.get("generic")).getCurrencyPair().base.getCurrencyCode());
  }

  @Test
  public void testAdaptOrderClosed() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/marketdata/example-orderClosed-update.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptOrderClosed(result);

    Assert.assertEquals(BigDecimal.valueOf(10000, 4), ((LimitOrder) testObj.get("generic")).getTradableAmount());
    Assert.assertEquals("GBP", ((LimitOrder) testObj.get("generic")).getCurrencyPair().counter.getCurrencyCode());
    Assert.assertEquals("BTC", ((LimitOrder) testObj.get("generic")).getCurrencyPair().base.getCurrencyCode());
  }

  @Test
  public void testAdaptOrdersMatched() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/marketdata/example-ordersMatched-update.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptOrdersMatched(result);

    Assert.assertEquals("211184", ((Trade) testObj.get("generic")).getId());
    Assert.assertEquals(BigDecimal.valueOf(4768, 4), ((Trade) testObj.get("generic")).getTradableAmount());
    Assert.assertEquals("GBP", ((Trade) testObj.get("generic")).getCurrencyPair().counter.getCurrencyCode());
    Assert.assertEquals("BTC", ((Trade) testObj.get("generic")).getCurrencyPair().base.getCurrencyCode());
    Assert.assertEquals(OrderType.ASK, ((Trade) testObj.get("generic")).getType());
  }

  @Test
  public void testAdaptBalancesChanged() throws IOException {

    // Read in the JSON from the example resources
    BufferedReader br = new BufferedReader(new FileReader("src/test/resources/account/example-balances-update.json"));

    String result = "", line;
    while ((line = br.readLine()) != null) {
      result += line.trim();
    }

    br.close();

    Map<String, Object> testObj = coinfloorAdapters.adaptBalancesChanged(result);

    Assert.assertEquals(BigDecimal.valueOf(990000, 2), ((Wallet) testObj.get("generic")).getBalance(Currency.GBP).getTotal());
  }

  /**
   * Experimental: USE WITH CAUTION. Adapters take every "BalancesUpdated" event, update local Wallet object with said new balance. This method
   * will return that cached Wallet object.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if getBalances method has not yet been called, or response has not been recieved.
   */
  @Test(expected = ExchangeException.class)
  public void getCachedAccountInfo() {

    if (cachedWallet == null) {
      throw new ExchangeException("getBalances method has not been called yet, or balance data has not been recieved!");
    }

  }

  /**
   * Experimental: USE WITH CAUTION. Adapters take every "OrderOpened," "OrdersMatched," or "OrderClosed" event, update local Orderbook object. This
   * method will return that cached Orderbook object. Notes: Will not survive program restarts, instantiated upon class instantiation with NO WALLETS.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if watchOrders method has not been called.
   */
  @Test(expected = ExchangeException.class)
  public void getCachedOrderBook() {

    if (cachedOrderBook == null) {
      throw new ExchangeException("watchOrders method has not been called yet!");
    }

  }

  /**
   * Experimental: USE WITH CAUTION. Adapters cache every "OrdersMatched" event, add the trade to a local Trades object. This method will return that
   * cached Trades object. Notes: Will not survive program restarts, will only cache user's transactions, unless \tWatchOrders method is called, in
   * which case it will cache ALL transctions happening on that market.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if watchOrders method has not been called, or no trades have occurred.
   */
  @Test(expected = ExchangeException.class)
  public void getCachedTrades() {

    if (cachedTrades == null) {
      throw new ExchangeException("watchOrders method has not been called yet, or no trades have occurred!");
    }

  }
}
