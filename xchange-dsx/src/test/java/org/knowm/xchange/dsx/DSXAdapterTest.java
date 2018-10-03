package org.knowm.xchange.dsx;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.dto.marketdata.DSXDepthJSONTest;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbook;
import org.knowm.xchange.dsx.dto.marketdata.DSXOrderbookWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXPairInfo;
import org.knowm.xchange.dsx.dto.marketdata.DSXTickerJSONTest;
import org.knowm.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.knowm.xchange.dsx.dto.marketdata.DSXTradesJSONTest;
import org.knowm.xchange.dsx.dto.marketdata.DSXTradesWrapper;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryJSONTest;
import org.knowm.xchange.dsx.dto.trade.DSXTradeHistoryReturn;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;

/**
 * Test for DSXAdapter class
 *
 * @author Mikhail Wall
 */
public class DSXAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    InputStream is =
        DSXDepthJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/marketdata/example-depth-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXOrderbookWrapper dsxOrderbookWrapper = mapper.readValue(is, DSXOrderbookWrapper.class);

    DSXOrderbook orderbookRaw =
        dsxOrderbookWrapper.getOrderbook(
            DSXAdapters.currencyPairToMarketName(CurrencyPair.BTC_USD));
    List<LimitOrder> asks =
        DSXAdapters.adaptOrders(orderbookRaw.getAsks(), CurrencyPair.BTC_USD, "ask", "");

    assertThat(asks.get(0).getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(asks.get(0).getTimestamp()).isNull();
    assertEquals(new BigDecimal("103.426"), asks.get(0).getLimitPrice());

    List<LimitOrder> bids =
        DSXAdapters.adaptOrders(orderbookRaw.getBids(), CurrencyPair.BTC_USD, "bid", "");

    LimitOrder bid1 = bids.get(0);
    assertThat(bid1.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(bid1.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(bid1.getTimestamp()).isNull();
    assertEquals(new BigDecimal("103.2"), bid1.getLimitPrice());
  }

  @Test
  public void testTradeAdapter() throws IOException {

    InputStream is =
        DSXTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/marketdata/example-trades-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXTradesWrapper dsxTradesWrapper = mapper.readValue(is, DSXTradesWrapper.class);

    Trades trades =
        DSXAdapters.adaptTrades(
            dsxTradesWrapper.getTrades(DSXAdapters.currencyPairToMarketName(CurrencyPair.BTC_USD)),
            CurrencyPair.BTC_USD);

    assertThat(trades.getTrades().size() == 150);

    assertThat(trades.getTrades().get(0).getPrice().toString()).isEqualTo("1588.09000");
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(Order.OrderType.BID);
    assertThat(trades.getTrades().get(0).getOriginalAmount().toString()).isEqualTo("0.03202392");
    assertThat(trades.getTrades().get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp()))
        .isEqualTo("2017-05-04 17:10:10 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        DSXTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DSXTickerWrapper dsxTickerWrapper = mapper.readValue(is, DSXTickerWrapper.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(
            dsxTickerWrapper
                .getTicker(DSXAdapters.currencyPairToMarketName(CurrencyPair.BTC_USD))
                .getLast())
        .isEqualTo(new BigDecimal("101.773"));
    Ticker ticker =
        DSXAdapters.adaptTicker(
            dsxTickerWrapper.getTicker(DSXAdapters.currencyPairToMarketName(CurrencyPair.BTC_USD)),
            CurrencyPair.BTC_USD);

    assertThat(ticker.getLast().toString()).isEqualTo("101.773");
    assertThat(ticker.getLow().toString()).isEqualTo("91.14");
    assertThat(ticker.getHigh().toString()).isEqualTo("109.88");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("1632898.2249"));
    assertThat(DateUtils.toUTCString(ticker.getTimestamp())).isEqualTo("2013-06-09 22:18:28 GMT");
  }

  @Test
  public void testUserTradeHistoryAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        DSXTradeHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DSXTradeHistoryReturn btceTradeHistory = mapper.readValue(is, DSXTradeHistoryReturn.class);

    Map<String, DSXPairInfo> pairs = new HashMap<>();
    pairs.put(
        "btcusd",
        new DSXPairInfo(
            5,
            new BigDecimal(5000),
            new BigDecimal(15000),
            new BigDecimal(0.0001),
            0,
            new BigDecimal(0),
            5,
            "USD",
            "BTC"));
    DSXExchangeInfo info = new DSXExchangeInfo(0L, pairs);
    DSXAdapters.dsxExchangeInfo = info;

    UserTrades trades = DSXAdapters.adaptTradeHistory(btceTradeHistory.getReturnValue());
    List<UserTrade> tradeList = trades.getUserTrades();
    UserTrade lastTrade = tradeList.get(tradeList.size() - 1);
    assertThat(lastTrade.getId()).isEqualTo("1000");
    assertThat(lastTrade.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(lastTrade.getPrice().toString()).isEqualTo("300");
    assertThat(lastTrade.getTimestamp().getTime()).isEqualTo(142123698000L);
    assertThat(DateUtils.toUTCString(lastTrade.getTimestamp()))
        .isEqualTo("1974-07-03 22:48:18 GMT");
    assertThat(lastTrade.getFeeAmount()).isEqualTo("0.001");
  }
}
