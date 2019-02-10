package org.knowm.xchange.wex.v3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepth;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepthJSONTest;
import org.knowm.xchange.wex.v3.dto.marketdata.WexDepthWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTickerJSONTest;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTickerWrapper;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTradesJSONTest;
import org.knowm.xchange.wex.v3.dto.marketdata.WexTradesWrapper;
import org.knowm.xchange.wex.v3.dto.trade.WexOrderInfoResult;
import org.knowm.xchange.wex.v3.dto.trade.WexOrderInfoReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeDataJSONTest;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeHistoryJSONTest;
import org.knowm.xchange.wex.v3.dto.trade.WexTradeHistoryReturn;

/** Tests the BTCEAdapter class */
public class WexAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexDepthWrapper bTCEDepthWrapper = mapper.readValue(is, WexDepthWrapper.class);

    WexDepth depthRaw = bTCEDepthWrapper.getDepth(WexAdapters.getPair(CurrencyPair.BTC_USD));
    List<LimitOrder> asks =
        WexAdapters.adaptOrders(depthRaw.getAsks(), CurrencyPair.BTC_USD, "ask", "");

    // verify all fields filled
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(asks.get(0).getTimestamp()).isNull();
    assertEquals(new BigDecimal("760.98"), asks.get(0).getLimitPrice());

    List<LimitOrder> bids =
        WexAdapters.adaptOrders(depthRaw.getBids(), CurrencyPair.BTC_USD, "bid", "");

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
    InputStream is =
        WexTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexTradesWrapper bTCETradesWrapper = mapper.readValue(is, WexTradesWrapper.class);

    Trades trades =
        WexAdapters.adaptTrades(
            bTCETradesWrapper.getTrades(WexAdapters.getPair(CurrencyPair.BTC_USD)),
            CurrencyPair.BTC_USD);
    // System.out.println(trades.getTrades().size());
    assertThat(trades.getTrades().size() == 150);

    // verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("760.999");
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(trades.getTrades().get(0).getOriginalAmount().toString()).isEqualTo("0.028354");
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    // assertThat("transactionCurrency should be PLN",
    // trades.getTrades().get(0).getTransactionCurrency().equals("PLN"));
    // System.out.println(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()));
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()))
        .isEqualTo("2013-11-23 11:10:04 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexTickerWrapper bTCETickerWrapper = mapper.readValue(is, WexTickerWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(bTCETickerWrapper.getTicker(WexAdapters.getPair(CurrencyPair.BTC_USD)).getLast())
        .isEqualTo(new BigDecimal("757"));
    Ticker ticker =
        WexAdapters.adaptTicker(
            bTCETickerWrapper.getTicker(WexAdapters.getPair(CurrencyPair.BTC_USD)),
            CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("757");
    assertThat(ticker.getLow().toString()).isEqualTo("655");
    assertThat(ticker.getHigh().toString()).isEqualTo("770");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("24620.6561"));
    assertThat(DateUtils.toUTCString(ticker.getTimestamp())).isEqualTo("2013-11-23 11:13:39 GMT");
  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexTradeHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexTradeHistoryReturn btceTradeHistory = mapper.readValue(is, WexTradeHistoryReturn.class);

    UserTrades trades = WexAdapters.adaptTradeHistory(btceTradeHistory.getReturnValue());
    List<UserTrade> tradeList = trades.getUserTrades();
    UserTrade lastTrade = tradeList.get(tradeList.size() - 1);
    assertThat(lastTrade.getId()).isEqualTo("7258275");
    assertThat(lastTrade.getType()).isEqualTo(OrderType.ASK);
    assertThat(lastTrade.getPrice().toString()).isEqualTo("125.75");
    assertThat(lastTrade.getTimestamp().getTime()).isEqualTo(1378194574000L);
    assertThat(DateUtils.toUTCString(lastTrade.getTimestamp()))
        .isEqualTo("2013-09-03 07:49:34 GMT");
    assertThat(lastTrade.getFeeAmount()).isNull();
  }

  @Test
  public void testOrderInfoAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexTradeDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/trade/example-order-info-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexOrderInfoReturn bTCEOrderInfoReturn = mapper.readValue(is, WexOrderInfoReturn.class);

    Map<Long, WexOrderInfoResult> rv = bTCEOrderInfoReturn.getReturnValue();

    assertThat(rv.keySet()).containsAll(Arrays.asList(343152L));

    LimitOrder order = WexAdapters.adaptOrderInfo("343152", rv.get(343152L));

    // verify all fields filled
    assertThat(order.getType()).isEqualTo(OrderType.ASK);
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertEquals(new BigDecimal("3.00000000"), order.getLimitPrice());
    assertEquals(new BigDecimal("2.00000000"), order.getOriginalAmount());
    assertEquals(new BigDecimal("1.00000000"), order.getCumulativeAmount());
    assertEquals(OrderStatus.PARTIALLY_FILLED, order.getStatus());
    assertThat(order.getTimestamp().getTime()).isEqualTo(1342448420000L);
  }
}
