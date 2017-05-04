package org.knowm.xchange.btcchina;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaTransaction;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetMarketDepthResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BTCChinaAdaptersTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testAdaptTickers() throws IOException {

    BTCChinaTicker btcChinaTicker = mapper.readValue(getClass().getResource("dto/marketdata/ticker-all-market.json"), BTCChinaTicker.class);
    Map<CurrencyPair, Ticker> tickers = BTCChinaAdapters.adaptTickers(btcChinaTicker);
    assertEquals(3, tickers.size());

    assertEquals(new HashSet<>(Arrays.asList(CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY, CurrencyPair.LTC_BTC)), tickers.keySet());

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
  public void testAdaptOrderBook() throws IOException {

    BTCChinaGetMarketDepthResponse response = mapper.readValue(getClass().getResourceAsStream("dto/trade/response/getMarketDepth2.json"),
        BTCChinaGetMarketDepthResponse.class);
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

    BTCChinaGetOrdersResponse response = mapper.readValue(getClass().getResource("dto/trade/response/getOrders-single-market-2-orders.json"),
        BTCChinaGetOrdersResponse.class);
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

    assertEquals(OrderStatus.NEW, BTCChinaAdapters.adaptOrderStatus("open"));
    assertEquals(OrderStatus.FILLED, BTCChinaAdapters.adaptOrderStatus("closed"));
    assertEquals(OrderStatus.CANCELED, BTCChinaAdapters.adaptOrderStatus("cancelled"));
    assertEquals(OrderStatus.PENDING_NEW, BTCChinaAdapters.adaptOrderStatus("pending"));
    assertEquals(OrderStatus.REJECTED, BTCChinaAdapters.adaptOrderStatus("error"));
    assertEquals(OrderStatus.REJECTED, BTCChinaAdapters.adaptOrderStatus("insufficient_balance"));
  }

  @Test
  public void testAdaptFundingHistory() throws JsonParseException, JsonMappingException, IOException {

    final BTCChinaGetDepositsResponse depositsResponse = mapper.readValue(getClass().getResource("dto/account/response/getDeposits.json"),
        BTCChinaGetDepositsResponse.class);
    final BTCChinaGetWithdrawalsResponse withdrawalResponse = mapper.readValue(getClass().getResource("dto/account/response/getWithdrawals.json"),
        BTCChinaGetWithdrawalsResponse.class);
    final List<FundingRecord> fundingRecords = BTCChinaAdapters.adaptFundingHistory(depositsResponse, withdrawalResponse);
    final FundingRecord depositRecord = fundingRecords.get(1);
    final FundingRecord withdrawalRecord = fundingRecords.get(3);

    assertEquals("mkrmyZyM9jBYGw5EB3wWmfgJ4Mvqnu7gEu", depositRecord.getAddress());
    assertEquals(Currency.BTC, depositRecord.getCurrency());
    assertEquals(new BigDecimal("2"), depositRecord.getAmount());
    assertEquals(FundingRecord.Status.COMPLETE, depositRecord.getStatus());

    assertEquals("15MGzXJnfugniyy7ZDw3hSjkm4tHPHzHba", withdrawalRecord.getAddress());
    assertEquals(Currency.BTC, withdrawalRecord.getCurrency());
    assertEquals(new BigDecimal("0.1"), withdrawalRecord.getAmount());
    assertEquals(FundingRecord.Status.PROCESSING, withdrawalRecord.getStatus());

    assertEquals(FundingRecord.Type.DEPOSIT, depositRecord.getType());
    assertEquals(FundingRecord.Type.WITHDRAWAL, withdrawalRecord.getType());
  }
}
