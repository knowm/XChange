package org.knowm.xchange.virtex.v2;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExDepthJSONTest;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExDepthWrapper;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTickerJSONTest;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTickerWrapper;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTradesJSONTest;
import org.knowm.xchange.virtex.v2.dto.marketdata.VirtExTradesWrapper;

/**
 * Tests the VirtExAdapter class
 */
public class VirtExAdapterTest {

  @Test
  public void testOrderAdapterWithDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExDepthJSONTest.class.getResourceAsStream("/marketdata/example-depth-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExDepthWrapper VirtExDepth = mapper.readValue(is, VirtExDepthWrapper.class);

    List<LimitOrder> asks = VirtExAdapters.adaptOrders(VirtExDepth.getDepth().getAsks(), CurrencyPair.BTC_CAD, "ask", "");

    // Verify all fields filled
    assertThat(asks.get(0).getLimitPrice().doubleValue()).isEqualTo(500000.000000000);
    assertThat(asks.get(0).getType()).isEqualTo(OrderType.ASK);
    assertThat(asks.get(0).getTradableAmount().doubleValue()).isEqualTo(7.000000000);
    assertThat(asks.get(0).getCurrencyPair().base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(asks.get(0).getCurrencyPair().counter.getCurrencyCode()).isEqualTo("CAD");

  }

  @Test
  public void testTradeAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTradesWrapper VirtExTrades = mapper.readValue(is, VirtExTradesWrapper.class);

    Trades trades = VirtExAdapters.adaptTrades(VirtExTrades.getTrades(), CurrencyPair.BTC_CAD);
    assertThat(trades.getTrades().size()).isEqualTo(632);

    // Verify all fields filled
    assertThat(trades.getTrades().get(0).getPrice().doubleValue() == 545.060000000);
    assertThat(trades.getTrades().get(0).getTradableAmount().doubleValue() == 1.189100000);
    assertThat(trades.getTrades().get(0).getCurrencyPair().base.getCurrencyCode() == "BTC");
    assertThat(DateUtils.toUTCString(trades.getTrades().get(0).getTimestamp())).isEqualTo("2014-05-21 20:45:15 GMT");
  }

  @Test
  public void testTickerAdapter() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VirtExTickerJSONTest.class.getResourceAsStream("/marketdata/example-ticker-data-v2.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    VirtExTickerWrapper VirtExTicker = mapper.readValue(is, VirtExTickerWrapper.class);

    Ticker ticker = VirtExAdapters.adaptTicker(VirtExTicker.getTicker(), CurrencyPair.BTC_CAD);
    System.out.println(ticker.toString());

    assertThat(ticker.getLast().toString()).isEqualTo("545.060000000");
    assertThat(ticker.getLow().toString()).isEqualTo("538.000000000");
    assertThat(ticker.getHigh().toString()).isEqualTo("574.000000000");
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("284.231600000"));

    assertThat(ticker.getCurrencyPair().base.getCurrencyCode()).isEqualTo("BTC");

  }
}
