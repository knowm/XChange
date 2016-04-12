package org.knowm.xchange.btcchina.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.btcchina.BTCChinaAdapters;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import org.knowm.xchange.btcchina.service.marketdata.BTCChinaDepthJSONTest;
import org.knowm.xchange.btcchina.service.marketdata.BTCChinaTickerJSONTest;
import org.knowm.xchange.btcchina.service.marketdata.BTCChinaTradesJSONTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;

/**
 * Tests the BTCChinaAdapter class
 */
public class BTCChinaAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCChinaDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaDepth BTCChinaDepth = mapper.readValue(is, BTCChinaDepth.class);

    List<LimitOrder> asks = BTCChinaAdapters.adaptOrders(BTCChinaDepth.getAsksArray(), CurrencyPair.BTC_CNY, OrderType.ASK);

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(1.0e+14);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(0.031);
    assertThat(asks.get(0).getCurrencyPair()).isEqualTo(CurrencyPair.BTC_CNY);

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCChinaTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, BTCChinaTrade.class);
    List<BTCChinaTrade> BTCChinaTrades = mapper.readValue(is, type);

    Trades trades = BTCChinaAdapters.adaptTrades(BTCChinaTrades, CurrencyPair.BTC_CNY);
    assertThat(trades.getTrades().size()).isEqualTo(101);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getType().equals(OrderType.BID));
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 4719);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 0.425);
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2014-01-03 16:04:24 GMT");
    System.out.println(trades.getTrades().get(0).toString());

  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCChinaTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTCChinaTicker BTCChinaTicker = mapper.readValue(is, BTCChinaTicker.class);

    Ticker ticker = BTCChinaAdapters.adaptTicker(BTCChinaTicker, CurrencyPair.BTC_CNY);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("546.00");
    assertThat(ticker.getLow().toString()).isEqualTo("545.00");
    assertThat(ticker.getHigh().toString()).isEqualTo("547.77");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("2593.89900000"));

  }
}
