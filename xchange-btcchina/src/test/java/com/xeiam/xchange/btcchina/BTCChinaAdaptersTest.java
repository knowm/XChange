package com.xeiam.xchange.btcchina;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrderStatus;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaTransaction;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class BTCChinaAdaptersTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testAdaptTickers() throws IOException {

    BTCChinaTicker btcChinaTicker = mapper.readValue(getClass().getResource("dto/marketdata/ticker-all-market.json"), BTCChinaTicker.class);
    Map<CurrencyPair, Ticker> tickers = BTCChinaAdapters.adaptTickers(btcChinaTicker);
    assertEquals(3, tickers.size());

    assertEquals(new HashSet<CurrencyPair>(Arrays.asList(CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY, CurrencyPair.LTC_BTC)), tickers.keySet());

    Ticker btccny = tickers.get(CurrencyPair.BTC_CNY);
    assertEquals(new BigDecimal("2894.97"), btccny.getHigh());
    assertEquals(new BigDecimal("2850.08"), btccny.getLow());
    assertEquals(new BigDecimal("2880.00"), btccny.getBid());
    assertEquals(new BigDecimal("2883.86"), btccny.getAsk());
    assertEquals(new BigDecimal("2880.00"), btccny.getLast());
    assertEquals(new BigDecimal("4164.41040000"), btccny.getVolume());
    assertEquals(1396412841000L, btccny.getTimestamp().getTime());
  }

  @Test
  public void testAdaptTransaction() {

    BTCChinaTransaction btcChinaTransaction = new BTCChinaTransaction(12158242, // id
        "sellbtc", // type
        new BigDecimal("-0.37460000"), // btc_amount
        new BigDecimal("0.00000000"), // ltc_amount
        new BigDecimal("1420.09151800"), // cny_amount
        1402922707L, // id
        "btccny" // market
    );
    Trade trade = BTCChinaAdapters.adaptTransaction(btcChinaTransaction);

    assertEquals(OrderType.ASK, trade.getType());
    assertEquals(new BigDecimal("0.37460000"), trade.getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, trade.getCurrencyPair());
    assertEquals(new BigDecimal("3790.95"), trade.getPrice());
    assertEquals(1402922707000L, trade.getTimestamp().getTime());
    assertEquals("12158242", trade.getId());
  }

  @Test
  public void testAdaptOrderBookBTCChinaDepth() throws IOException {

    BTCChinaDepth btcChinaDepth = mapper.readValue(getClass().getResource("/marketdata/example-depth-data.json"), BTCChinaDepth.class);
    OrderBook orderBook = BTCChinaAdapters.adaptOrderBook(btcChinaDepth, CurrencyPair.BTC_CNY);

    List<LimitOrder> bids = orderBook.getBids();
    List<LimitOrder> asks = orderBook.getAsks();

    // bid 4.51@544.83
    assertEquals(new BigDecimal("544.83"), bids.get(0).getLimitPrice());
    assertEquals(new BigDecimal("4.51"), bids.get(0).getTradableAmount());

    // bid 4.19@543.38
    assertEquals(new BigDecimal("543.38"), bids.get(1).getLimitPrice());
    assertEquals(new BigDecimal("4.19"), bids.get(1).getTradableAmount());

    // ask 49.234@546
    assertEquals(new BigDecimal("546"), asks.get(0).getLimitPrice());
    assertEquals(new BigDecimal("49.234"), asks.get(0).getTradableAmount());

    // ask 10.934@547
    assertEquals(new BigDecimal("547"), asks.get(1).getLimitPrice());
    assertEquals(new BigDecimal("10.934"), asks.get(1).getTradableAmount());
  }

  @Test
  public void testAdaptOrderBook() throws IOException {

    BTCChinaGetMarketDepthResponse response = mapper.readValue(getClass().getResourceAsStream("dto/trade/response/getMarketDepth2.json"), BTCChinaGetMarketDepthResponse.class);
    OrderBook orderBook = BTCChinaAdapters.adaptOrderBook(response.getResult().getMarketDepth(), CurrencyPair.BTC_CNY);
    List<LimitOrder> bids = orderBook.getBids();
    List<LimitOrder> asks = orderBook.getAsks();
    assertEquals(2, bids.size());
    assertEquals(2, asks.size());

    // bid 1@99
    assertEquals(new BigDecimal("99"), bids.get(0).getLimitPrice());
    assertEquals(new BigDecimal("1"), bids.get(0).getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, bids.get(0).getCurrencyPair());
    assertEquals(OrderType.BID, bids.get(0).getType());

    // bid 2@98
    assertEquals(new BigDecimal("98"), bids.get(1).getLimitPrice());
    assertEquals(new BigDecimal("2"), bids.get(1).getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, bids.get(1).getCurrencyPair());
    assertEquals(OrderType.BID, bids.get(1).getType());

    // ask 0.997@100
    assertEquals(new BigDecimal("100"), asks.get(0).getLimitPrice());
    assertEquals(new BigDecimal("0.997"), asks.get(0).getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, asks.get(0).getCurrencyPair());
    assertEquals(OrderType.ASK, asks.get(0).getType());

    // ask 2@101
    assertEquals(new BigDecimal("101"), asks.get(1).getLimitPrice());
    assertEquals(new BigDecimal("2"), asks.get(1).getTradableAmount());
    assertEquals(CurrencyPair.BTC_CNY, asks.get(1).getCurrencyPair());
    assertEquals(OrderType.ASK, asks.get(1).getType());

    assertEquals(1407060232000L, orderBook.getTimeStamp().getTime());
  }

  @Test
  public void testAdaptOrders() throws IOException {

    BTCChinaGetOrdersResponse response = mapper.readValue(getClass().getResource("dto/trade/response/getOrders-single-market-2-orders.json"), BTCChinaGetOrdersResponse.class);
    List<LimitOrder> limitOrders = BTCChinaAdapters.adaptOrders(response.getResult(), null);
    assertEquals(2, limitOrders.size());
    LimitOrder order = limitOrders.get(0);
    assertEquals("13942927", order.getId());
    assertEquals(OrderType.BID, order.getType());
    assertEquals(new BigDecimal("2000.00"), order.getLimitPrice());
    assertEquals(CurrencyPair.BTC_CNY, order.getCurrencyPair());
    assertEquals(new BigDecimal("0.00100000"), order.getTradableAmount());
    assertEquals(1396255376000L, order.getTimestamp().getTime());
  }

  @Test
  public void testAdaptOrderStatus() {

    assertEquals(BTCChinaOrderStatus.OPEN, BTCChinaAdapters.adaptOrderStatus("open"));
    assertEquals(BTCChinaOrderStatus.CLOSED, BTCChinaAdapters.adaptOrderStatus("closed"));
    assertEquals(BTCChinaOrderStatus.CANCELLED, BTCChinaAdapters.adaptOrderStatus("cancelled"));
    assertEquals(BTCChinaOrderStatus.PENDING, BTCChinaAdapters.adaptOrderStatus("pending"));
    assertEquals(BTCChinaOrderStatus.ERROR, BTCChinaAdapters.adaptOrderStatus("error"));
    assertEquals(BTCChinaOrderStatus.INSUFFICIENT_BALANCE, BTCChinaAdapters.adaptOrderStatus("insufficient_balance"));
  }

}
