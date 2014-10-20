package com.xeiam.xchange.btce.v3.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v3.BTCEAdapters;
import com.xeiam.xchange.btce.v3.BTCEUtils;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepth;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEDepthWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCETradesWrapper;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;
import com.xeiam.xchange.btce.v3.service.marketdata.BTCEDepthJSONTest;
import com.xeiam.xchange.btce.v3.service.marketdata.BTCETickerJSONTest;
import com.xeiam.xchange.btce.v3.service.marketdata.BTCETradesJSONTest;
import com.xeiam.xchange.btce.v3.service.trade.BTCETradeHistoryJSONTest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Tests the BTCEAdapter class
 */
public class BTCEAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCEDepthJSONTest.class.getResourceAsStream("/v3/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCEDepthWrapper bTCEDepthWrapper = mapper.readValue(is, BTCEDepthWrapper.class);

    BTCEDepth depthRaw = bTCEDepthWrapper.getDepth(BTCEUtils.getPair(CurrencyPair.BTC_USD));
    List<LimitOrder> asks = BTCEAdapters.adaptOrders(depthRaw.getAsks(), CurrencyPair.BTC_USD, "ask", "");

    // verify all fields filled
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(asks.get(0).getTimestamp()).isNull();
    assertEquals(new BigDecimal("760.98"), asks.get(0).getLimitPrice());

    List<LimitOrder> bids = BTCEAdapters.adaptOrders(depthRaw.getBids(), CurrencyPair.BTC_USD, "bid", "");

    // verify all fields filled
    LimitOrder bid1 = bids.get(0);
    assertThat(bid1.getType()).isEqualTo(OrderType.BID);
    assertThat(bid1.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(bid1.getTimestamp()).isNull();
    assertEquals(new BigDecimal("758.99"), bid1.getLimitPrice());

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradesJSONTest.class.getResourceAsStream("/v3/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETradesWrapper bTCETradesWrapper = mapper.readValue(is, BTCETradesWrapper.class);

    Trades trades = BTCEAdapters.adaptTrades(bTCETradesWrapper.getTrades(com.xeiam.xchange.btce.v3.BTCEUtils.getPair(CurrencyPair.BTC_USD)), CurrencyPair.BTC_USD);
    // System.out.println(trades.getTrades().size());
    assertThat(trades.getTrades().size() == 150);

    // verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("760.999");
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(trades.getTrades().get(0).getTradableAmount().toString()).isEqualTo("0.028354");
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    // assertThat("transactionCurrency should be PLN",
    // trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    // System.out.println(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()));
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2013-11-23 11:10:04 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETickerJSONTest.class.getResourceAsStream("/v3/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETickerWrapper bTCETickerWrapper = mapper.readValue(is, BTCETickerWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)).getLast()).isEqualTo(new BigDecimal("757"));
    Ticker ticker = BTCEAdapters.adaptTicker(bTCETickerWrapper.getTicker(BTCEUtils.getPair(CurrencyPair.BTC_USD)), CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("757");
    assertThat(ticker.getLow().toString()).isEqualTo("655");
    assertThat(ticker.getHigh().toString()).isEqualTo("770");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("24620.6561"));
    assertThat(DateUtils.toUTCString(ticker.getTimestamp())).isEqualTo("2013-11-23 11:13:39 GMT");

  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradeHistoryJSONTest.class.getResourceAsStream("/v3/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCETradeHistoryReturn btceTradeHistory = mapper.readValue(is, BTCETradeHistoryReturn.class);

    Trades trades = BTCEAdapters.adaptTradeHistory(btceTradeHistory.getReturnValue());
    List<Trade> tradeList = trades.getTrades();
    Trade lastTrade = tradeList.get(tradeList.size() - 1);
    assertThat(lastTrade.getId()).isEqualTo("7258275");
    assertThat(lastTrade.getType()).isEqualTo(OrderType.ASK);
    assertThat(lastTrade.getPrice().toString()).isEqualTo("125.75");
    assertThat(lastTrade.getTimestamp().getTime()).isEqualTo(1378194574000L);
    assertThat(DateUtils.toUTCString(lastTrade.getTimestamp())).isEqualTo("2013-09-03 07:49:34 GMT");

  }
}
